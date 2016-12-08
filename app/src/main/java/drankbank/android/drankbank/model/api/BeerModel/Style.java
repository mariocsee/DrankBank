package drankbank.android.drankbank.model.api.BeerModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Veronica on 12/6/16.
 */


public class Style {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("category")
    @Expose
    public Category category;
    @SerializedName("srmMax")
    @Expose
    public String srmMax;
    @SerializedName("ibuMax")
    @Expose
    public String ibuMax;
    @SerializedName("srmMin")
    @Expose
    public String srmMin;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("fgMin")
    @Expose
    public String fgMin;
    @SerializedName("ibuMin")
    @Expose
    public String ibuMin;
    @SerializedName("createDate")
    @Expose
    public String createDate;
    @SerializedName("fgMax")
    @Expose
    public String fgMax;
    @SerializedName("abvMax")
    @Expose
    public String abvMax;
    @SerializedName("ogMin")
    @Expose
    public String ogMin;
    @SerializedName("abvMin")
    @Expose
    public String abvMin;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("categoryId")
    @Expose
    public Integer categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSrmMax() {
        return srmMax;
    }

    public void setSrmMax(String srmMax) {
        this.srmMax = srmMax;
    }

    public String getIbuMax() {
        return ibuMax;
    }

    public void setIbuMax(String ibuMax) {
        this.ibuMax = ibuMax;
    }

    public String getSrmMin() {
        return srmMin;
    }

    public void setSrmMin(String srmMin) {
        this.srmMin = srmMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFgMin() {
        return fgMin;
    }

    public void setFgMin(String fgMin) {
        this.fgMin = fgMin;
    }

    public String getIbuMin() {
        return ibuMin;
    }

    public void setIbuMin(String ibuMin) {
        this.ibuMin = ibuMin;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFgMax() {
        return fgMax;
    }

    public void setFgMax(String fgMax) {
        this.fgMax = fgMax;
    }

    public String getAbvMax() {
        return abvMax;
    }

    public void setAbvMax(String abvMax) {
        this.abvMax = abvMax;
    }

    public String getOgMin() {
        return ogMin;
    }

    public void setOgMin(String ogMin) {
        this.ogMin = ogMin;
    }

    public String getAbvMin() {
        return abvMin;
    }

    public void setAbvMin(String abvMin) {
        this.abvMin = abvMin;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
