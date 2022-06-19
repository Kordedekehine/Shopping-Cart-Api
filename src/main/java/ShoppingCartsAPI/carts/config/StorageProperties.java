package ShoppingCartsAPI.carts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir"; //upload directory

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}