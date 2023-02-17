package cava.tags

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import org.hibernate.FetchMode as FM
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import java.nio.charset.StandardCharsets

@Service(Tag)
abstract class TagService implements ITagService {

    /**
     * Returns a sorted and paginated List of tags.  Optionally searches for 'term'
     * @param args
     * @return
     */
    List search(Map args) {

        Closure query = {
            if (args.term) {
                or {
                    ilike("title", "%${ args.term }%")
                    ilike("description", "%${ args.term }%")
                }
            }
            fetchMode 'media', FM.JOIN
            fetchMode 'tags', FM.JOIN
            order(args.sort, args.order)
            maxResults(args.max)
            firstResult(args.offset)
            setReadOnly true
        }

        List results = Tag.createCriteria().list(['max':args.max, 'offset':args.offset], query)

        return results.unique()
    }

    /**
     *
     * @param title
     * @return
     */
    Tag findByTitle(String title) {

        Closure query = {
            eq("urlTitle", title)
            fetchMode 'media', FM.JOIN
            fetchMode 'tags', FM.JOIN
            maxResults(1)
            setReadOnly true
        }

        List results = Tag.createCriteria().list(query)

        Tag tag = null

        if (results.size() > 0) {
            tag = results[0] as Tag
        }
    }

    /**
     *
     * @param path
     * @return
     */
    @Transactional
    boolean createTagsFromText (String path) {

        try {

            String filePath = path + "/files/ref_des.docx"

            FileInputStream fis = new FileInputStream(filePath)
            if (fis == null) {
                log.error("The fileInputStream is null")
                return false
            }

            XWPFDocument xdoc = new XWPFDocument(fis)
            if (xdoc == null) {
                log.error("The XWPDocument is null")
                return false
            }

            List paragraphList = xdoc.getParagraphs()
            if (paragraphList.size() == 0) {
                log.error("There is no text")
                return false
            }

            int counter = 0
            String parentTag = ""

            Media media = new Media(title: "Media 1", url: "https://interactiveoceans.washington.edu/placeholder20230215091022/", type: Media.Type.VIDEO).save(flush:true)
            Media media2 = new Media(title: "Media 2", url: "https://interactiveoceans.washington.edu/img_8704/", type: Media.Type.IMAGE).save(flush:true)

            for (XWPFParagraph paragraph in paragraphList) {

                if (paragraph.getText().size() < 13) { // Reference designators should have at least have 13 characters
                    continue
                }

                String refDes = paragraph.getText().toString().trim()

                if (!refDes.contains("-")) {
                    continue
                }

                Tag tag = Tag.findByTitle(refDes)

                if (tag) {
                    continue
                }

                if (refDes.findAll({it -> it == "-"}).size() == 1) {

                    String url = URLEncoder.encode(refDes.trim() , StandardCharsets.UTF_8.toString())

                    tag = new Tag(title: refDes, urlTitle: url)

                    if (!tag.validate()) {
                        log.error("Tag ${tag} could not be validated because of ${tag.errors}")
                        continue
                    }
                    tag.addToMedia(media)
                    tag.addToMedia(media2)
                    tag.save(flush:true)
                    parentTag = tag.title
                }

                if (refDes.findAll({it -> it == "-"}).size() > 1 && refDes.contains(parentTag)) {

                    String[] parts = refDes.split("-")

                    if (parts.size() < 4) {
                        log.error("The reference designator has too few parts (it needs 3)")
                        continue
                    }

                    String title = parts[0] + "-" + parts[1] + "-" + parts[2] + "-" + parts[3]

                    String url = URLEncoder.encode(refDes.trim() , StandardCharsets.UTF_8.toString())

                    Tag associatedTag = new Tag (title: title, urlTitle: url)

                    if (!associatedTag.validate()) {
                        log.error("The Tag ${associatedTag} could not be validated because of ${associatedTag.errors}")
                        continue
                    }
                    associatedTag.save(flush:true)

                    Tag parent = Tag.findByTitle(parentTag)

                    Tag child = Tag.findByTitle(title)

                    if (parent != null && child != null) {

                        TagAssociation tagAssociation = new TagAssociation(tag: parent, otherTag: child).save(flush:true)
                        TagAssociation otherTagAssociation = new TagAssociation(tag: child, otherTag: parent).save(flush:true)
                    }
                }
                counter ++
            }


        } catch (Exception ex) {
            ex.printStackTrace()
            return false
        }
        return true
    }

}