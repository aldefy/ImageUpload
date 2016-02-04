package techgravy.imageuploadtest.models;

/**
 * Created by aditlal on 04/02/16.
 */
public class Data {
    private String animated;

    private String account_id;

    private String link;

    private String width;

    private String favorite;

    private String type;

    private String size;

    private String bandwidth;

    private String id;

    private String title;

    private String height;

    private String description;

    private String views;

    private String name;

    private String deletehash;

    private String datetime;

    public String getAnimated() {
        return animated;
    }

    public void setAnimated(String animated) {
        this.animated = animated;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeletehash() {
        return deletehash;
    }

    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [animated = "+animated+", account_id = "+account_id+", link = "+link+", width = "+width+", favorite = "+favorite+", type = "+type+", size = "+size+", bandwidth = "+bandwidth+", id = "+id+", title = "+title+", height = "+height+", description = "+description+", views = "+views+", name = "+name+", deletehash = "+deletehash+", datetime = "+datetime+"]";
    }
}
