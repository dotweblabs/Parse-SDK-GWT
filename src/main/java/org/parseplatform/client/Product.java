package org.parseplatform.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.reflect.client.strategy.ReflectionStrategy;
import org.parseplatform.client.annotations.Column;
import org.parseplatform.client.annotations.RuntimeRetention;

import java.util.LinkedList;
import java.util.List;

public class Product implements Model {
    @Column
    public String objectId;

    @Column
    public String createdAt;

    @Column
    public String updatedAt;

    @Column
    public ParseACL ACL;

    @Column
    public String nameOfVariant;

    @Column
    public Boolean haveTax;

    @Column
    public List<DayPrice> dayDependent;

    @Column
    public Double price;

    @Column
    public String name;

    @Column
    public String otherDescription;

    @Column
    public Boolean isDayDependent;

    @Column
    public String addOnsKey;

    @Column
    public List<String> variants;

    @Column
    public Boolean canBePartial;

    @Column
    public Boolean isBooking;

    @Column
    public Boolean isOnlineProduct;

    @Column
    public Boolean isJumpProduct;

    @Column
    public Boolean isUnlimited;

    @Column
    public Boolean isCanWithJumps;

    @Column
    public Boolean isCanWithJump;

    @Column
    public Double pointPrice;

    @Column
    public Long minimumQuantity;

    @Column
    public String otherName;

    @Column
    public Boolean isVariant;

    @Column
    public String description;

    @Column
    public String sku;

    @Column
    public Boolean haveVariant;

    @Column
    public Boolean isNeedCustomer;

    @Column
    public ParsePointer category;
    //NOT INCLUDED IN DATABASE
    public Long quantity;
    public Double total;
    public ParseFile barcode;

    @Column
    public String id;

    public Long barcodeId;

    public Product() {}

    public Product(String id, String name, Double price) {
        setObjectId(id);
        setName(name);
        setPrice(price);
    }

    public Product(ParseObject parseObject) {
        if(parseObject != null) {

            // TODO Missing fields
            String objectId = (parseObject.get("objectId") != null && parseObject.get("objectId").isString() != null) ? parseObject.get("objectId").isString().stringValue() : null;
            String createdAt = (parseObject.get("createdAt") != null && parseObject.get("createdAt").isString() != null) ? parseObject.get("createdAt").isString().stringValue() : null;
            String updatedAt = (parseObject.get("updatedAt") != null && parseObject.get("updatedAt").isString() != null) ? parseObject.get("updatedAt").isString().stringValue() : null;
            ParseACL acl = (parseObject.get("ACL") != null && parseObject.get("ACL").isObject() != null)
                    ? new ParseACL(parseObject.get("ACL").isObject()) : null;

//            ParsePointer category = (parseObject.get("category") != null && parseObject.get("category").isObject() != null)
//                    ? (ParsePointer) parseObject.get("category").isObject() : null;

            String nameOfVariant = (parseObject.get("nameOfVariant") != null && parseObject.get("nameOfVariant").isString() != null) ? parseObject.get("nameOfVariant").isString().stringValue(): null;
            Boolean haveTax = (parseObject.get("haveTax") != null && parseObject.get("haveTax").isBoolean() != null) ? parseObject.get("haveTax").isBoolean().booleanValue(): null;
            JSONArray dayDependent = (parseObject.get("dayDependent") != null && parseObject.get("dayDependent").isArray() != null) ? parseObject.get("dayDependent").isArray(): null;
            Double price = (parseObject.get("price") != null && parseObject.get("price").isNumber() != null) ? parseObject.get("price").isNumber().doubleValue(): null;
            String name = (parseObject.get("name") != null && parseObject.get("name").isString() != null) ? parseObject.get("name").isString().stringValue(): null;
            String otherDescription = (parseObject.get("otherDescription") != null && parseObject.get("otherDescription").isString() != null) ? parseObject.get("otherDescription").isString().stringValue(): null;
            Boolean isDayDependent = (parseObject.get("isDayDependent") != null && parseObject.get("isDayDependent").isBoolean() != null) ? parseObject.get("isDayDependent").isBoolean().booleanValue(): null;
            String addOnsKey = (parseObject.get("addOnsKey") != null && parseObject.get("addOnsKey").isString() != null) ? parseObject.get("addOnsKey").isString().stringValue(): null;
            JSONArray variants = (parseObject.get("variants") != null && parseObject.get("variants").isArray() != null) ? parseObject.get("variants").isArray(): null;
            Boolean canBePartial = (parseObject.get("canBePartial") != null && parseObject.get("canBePartial").isBoolean() != null) ? parseObject.get("canBePartial").isBoolean().booleanValue(): null;
            Boolean isBooking = (parseObject.get("isBooking") != null && parseObject.get("isBooking").isBoolean() != null) ? parseObject.get("isBooking").isBoolean().booleanValue(): null;
            Boolean isOnlineProduct = (parseObject.get("isOnlineProduct") != null && parseObject.get("isOnlineProduct").isBoolean() != null) ? parseObject.get("isOnlineProduct").isBoolean().booleanValue(): null;
            Boolean isJumpProduct = (parseObject.get("isJumpProduct") != null && parseObject.get("isJumpProduct").isBoolean() != null) ? parseObject.get("isJumpProduct").isBoolean().booleanValue(): null;
            Boolean isUnlimited = (parseObject.get("isUnlimited") != null && parseObject.get("isUnlimited").isBoolean() != null) ? parseObject.get("isUnlimited").isBoolean().booleanValue(): null;
            Boolean isCanWithJumps = (parseObject.get("isCanWithJumps") != null && parseObject.get("isCanWithJumps").isBoolean() != null) ? parseObject.get("isCanWithJumps").isBoolean().booleanValue(): null;
            Boolean isCanWithJump = (parseObject.get("isCanWithJump") != null && parseObject.get("isCanWithJump").isBoolean() != null) ? parseObject.get("isCanWithJump").isBoolean().booleanValue(): null;
            Double pointPrice = (parseObject.get("pointPrice") != null && parseObject.get("pointPrice").isNumber() != null) ? parseObject.get("pointPrice").isNumber().doubleValue(): null;
            Double dMinimumQuantity = (parseObject.get("minimumQuantity") != null && parseObject.get("minimumQuantity").isNumber() != null) ? parseObject.get("minimumQuantity").isNumber().doubleValue(): null;
            Long minimumQuantity = dMinimumQuantity.longValue();
            String otherName = (parseObject.get("otherName") != null && parseObject.get("otherName").isString() != null) ? parseObject.get("otherName").isString().stringValue(): null;
            Boolean isVariant = (parseObject.get("isVariant") != null && parseObject.get("isVariant").isBoolean() != null) ? parseObject.get("isVariant").isBoolean().booleanValue(): null;
            String description = (parseObject.get("description") != null && parseObject.get("description").isString() != null) ? parseObject.get("description").isString().stringValue(): null;
            String sku = (parseObject.get("sku") != null && parseObject.get("sku").isString() != null) ? parseObject.get("sku").isString().stringValue(): null;
            Boolean haveVariant = (parseObject.get("haveVariant") != null && parseObject.get("haveVariant").isBoolean() != null) ? parseObject.get("haveVariant").isBoolean().booleanValue(): null;
            Boolean isNeedCustomer = (parseObject.get("isNeedCustomer") != null && parseObject.get("isNeedCustomer").isBoolean() != null) ? parseObject.get("isNeedCustomer").isBoolean().booleanValue(): null;


                setObjectId(objectId);
                setCreatedAt(createdAt);
                setUpdatedAt(updatedAt);
                setACL(acl);
//                setCategory(category);
                setNameOfVariant(nameOfVariant);
                setHaveTax(haveTax);
                setPrice(price);
                setName(name);
                setOtherDescription(otherDescription);
                setIsDayDependent(isDayDependent);
                setAddOnsKey(addOnsKey);

            if(dayDependent != null) {
                List<DayPrice> dayDependentList = new LinkedList<DayPrice>();
                // TODO: add function to populate list
                setDayDependent(dayDependentList);
            }

            if(variants != null) {
                List<String> variantsList = new LinkedList<String>();
                // TODO: add function to populate list
                setVariants(variantsList);
            }
            setCanBePartial(canBePartial);
            setBooking(isBooking);
            setOnlineProduct(isOnlineProduct);
            setJumpProduct(isJumpProduct);
            setUnlimited(isUnlimited);
            setCanWithJumps(isCanWithJumps);
            setCanWithJump(isCanWithJump);
            setPointPrice(pointPrice);
            setMinimumQuantity(minimumQuantity);
            setOtherName(otherName);
            setVariant(isVariant);
            setDescription(description);
            setSku(sku);
            setHaveVariant(haveVariant);
            setNeedCustomer(isNeedCustomer);

        }
    }

    public Product(String sku, String id, String name, String otherName, Long quantity, Double total) {
        setSku(sku);
        setId(id);
        setName(name);
        setOtherName(otherName);
        setQuantity(quantity);
        setTotal(total);
    }

    @Override
    public Model fromParseObject(ParseObject parseObject) {
        Product product = new Product(parseObject);
        return product;
    }

    @Override
    public ParseObject toParseObject() {


        ParseObject parseObject = new ParseObject();
        parseObject.setClassName(ParseObject.class.getName());

        if(objectId != null){
            parseObject.putString("objectId", objectId);
        }
        ParseDate createdAtDate = new ParseDate(createdAt);
        ParseDate updatedAtDate = new ParseDate(updatedAt);

        parseObject.put("createdAt",createdAtDate);
        parseObject.put("updatedAt",updatedAtDate);
        parseObject.setACL(ACL);
//        parseObject.put("category",category);
        parseObject.putString("nameOfVariant",nameOfVariant);
        parseObject.putBoolean("haveTax",haveTax);


        JSONArray dependentArray = new JSONArray();
        int i=0;
        for(DayPrice dayPrice : dayDependent) {
            // TODO: fix this
            //dependentArray.set(i, dayPrice.toParseObject());
            //i++;
        }
        parseObject.put("dayDependent",dependentArray);
        parseObject.putNumber("price",price);
        parseObject.putString("name",name);
        parseObject.putString("otherDescription",otherDescription);
        parseObject.putBoolean("isDayDependent",isDayDependent);
        parseObject.putString("addOnsKey",addOnsKey);

        JSONArray variantsArray = new JSONArray();
        int o=0;
        for(String variant : variants) {
            // TODO: fix this
            //variantsArray.set(o, product.toParseObject());
            //o++;
        }
        parseObject.put("variants",variantsArray);
        parseObject.putBoolean("canBePartial",canBePartial);
        parseObject.putBoolean("isBooking",isBooking);
        parseObject.putBoolean("isOnlineProduct",isOnlineProduct);
        parseObject.putBoolean("isJumpProduct",isJumpProduct);
        parseObject.putBoolean("isUnlimited",isUnlimited);
        parseObject.putBoolean("isCanWithJumps",isCanWithJumps);
        parseObject.putBoolean("isCanWithJump",isCanWithJump);
        parseObject.putNumber("pointPrice",pointPrice);
        parseObject.putNumber("minimumQuantity",minimumQuantity);
        parseObject.putString("otherName",otherName);
        parseObject.putBoolean("isVariant",isVariant);
        parseObject.putString("description",description);
        parseObject.putString("sku",sku);
        parseObject.putBoolean("haveVariant",haveVariant);
        parseObject.putBoolean("isNeedCustomer",isNeedCustomer);


        return parseObject;
    }

    public static Product fromJSONObject(JSONObject object){
        if(object == null){
            return null;
        }
        Product product = new Product();
        product.setObjectId(object.get("id") != null ? object.get("id").isString().stringValue() : null);
        product.setName(object.get("name") != null ? object.get("name").isString().stringValue() : null);
//        product.setDayDependent(object.get("dayDependent") != null ? object.get("dayDependent").isArray() : null);
        if(object.get("price") != null && object.get("price").isNumber() != null){
            product.setPrice(object.get("price") != null ? object.get("price").isNumber().doubleValue() : null);
        }
        if(object.get("isDayDependent") != null && object.get("isDayDependent").isBoolean() != null){
            product.setIsDayDependent(object.get("isDayDependent") != null ? object.get("isDayDependent").isBoolean().booleanValue() : null);
        }
        if(object.get("isBooking") != null && object.get("isBooking").isBoolean() != null){
            product.setBooking(object.get("isBooking") != null ? object.get("isBooking").isBoolean().booleanValue() : null);
        }
        product.setSku(object.get("sku") != null ? object.get("sku").isString().stringValue() : null);
        product.setOtherName(object.get("otherName") != null ? object.get("otherName").isString().stringValue() : null);
        return product;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getBooking() {
        return isBooking;
    }

    public void setBooking(Boolean booking) {
        isBooking = booking;
    }

    public Boolean getHaveTax() {
        return haveTax;
    }

    public Boolean getIsDayDependent() {
        return isDayDependent;
    }

    public List<DayPrice> getDayDependent(){
        if(dayDependent == null) {
            dayDependent = new LinkedList<DayPrice>();
        }

        return dayDependent;
    }

    public void setDayDependent(List<DayPrice> dayDependent) {
        this.dayDependent = dayDependent;
    }

    public void setIsDayDependent(Boolean dayDependent) {
        isDayDependent = dayDependent;
    }

    public void setHaveTax(Boolean haveTax) {
        this.haveTax = haveTax;
    }

    @Override
    public String toString() {
        String s = "";
        s = s + "SKU: " + sku + s
                + s + "id: " + id + "\n"
                + s + "name: " + name + "\n"
                + s + "otherName: " + otherName + "\n"
                + s + "qty: " + quantity + "\n"
                + s + "id: " + id;
        return s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ParseACL getACL() {
        return ACL;
    }

    public void setACL(ParseACL ACL) {
        this.ACL = ACL;
    }

    public ParseFile getBarcode() {
        return barcode;
    }

    public void setBarcode(ParseFile barcode) {
        this.barcode = barcode;
    }

    public String getNameOfVariant() {
        return nameOfVariant;
    }

    public void setNameOfVariant(String nameOfVariant) {
        this.nameOfVariant = nameOfVariant;
    }

    public String getAddOnsKey() {
        return addOnsKey;
    }

    public void setAddOnsKey(String addOnsKey) {
        this.addOnsKey = addOnsKey;
    }

    public List<String> getVariants() {
        if(variants == null) {
            variants = new LinkedList<String>();
        }
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public Boolean getCanBePartial() {
        return canBePartial;
    }

    public void setCanBePartial(Boolean canBePartial) {
        this.canBePartial = canBePartial;
    }

    public Boolean getOnlineProduct() {
        return isOnlineProduct;
    }

    public void setOnlineProduct(Boolean onlineProduct) {
        isOnlineProduct = onlineProduct;
    }

    public Boolean getJumpProduct() {
        return isJumpProduct;
    }

    public void setJumpProduct(Boolean jumpProduct) {
        isJumpProduct = jumpProduct;
    }

    public Boolean getUnlimited() {
        return isUnlimited;
    }

    public void setUnlimited(Boolean unlimited) {
        isUnlimited = unlimited;
    }

    public Boolean getCanWithJumps() {
        return isCanWithJumps;
    }

    public void setCanWithJumps(Boolean canWithJumps) {
        isCanWithJumps = canWithJumps;
    }

    public Boolean getCanWithJump() {
        return isCanWithJump;
    }

    public void setCanWithJump(Boolean canWithJump) {
        isCanWithJump = canWithJump;
    }

    public Double getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(Double pointPrice) {
        this.pointPrice = pointPrice;
    }

    public Long getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Long minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Long getBarcodeId() {
        return barcodeId;
    }

    public void setBarcodeId(Long barcodeId) {
        this.barcodeId = barcodeId;
    }

    public Boolean getHaveVariant() {
        return haveVariant;
    }

    public void setHaveVariant(Boolean haveVariant) {
        this.haveVariant = haveVariant;
    }

    public Boolean getNeedCustomer() {
        return isNeedCustomer;
    }

    public void setNeedCustomer(Boolean needCustomer) {
        isNeedCustomer = needCustomer;
    }

    public ParsePointer getCategory() {
        return category;
    }

    public void setCategory(ParsePointer category) {
        this.category = category;
    }

    public Boolean getVariant() {
        return isVariant;
    }

    public void setVariant(Boolean variant) {
        isVariant = variant;
    }
}
