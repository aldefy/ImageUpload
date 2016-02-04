package techgravy.imageuploadtest.models;

/**
 * Created by aditlal on 04/02/16.
 */
public class ImageResponse {
    private String status;

    private Data data;

    private String success;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", data = "+data+", success = "+success+"]";
    }
}
