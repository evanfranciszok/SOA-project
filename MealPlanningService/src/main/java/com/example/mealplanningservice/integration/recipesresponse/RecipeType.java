//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.04.18 at 12:42:35 PM CEST 
//


package com.example.mealplanningservice.integration.recipesresponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for RecipeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecipeType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="userId" type="{http://www.example.com/api/events/recipesresponse}UserIdType"/&gt;
 *         &lt;element name="id" type="{http://www.example.com/api/events/recipesresponse}IdType"/&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ingredient" type="{http://www.example.com/api/events/recipesresponse}IngredientType" maxOccurs="unbounded"/&gt;
 *         &lt;element name="NER" type="{http://www.example.com/api/events/recipesresponse}NerType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecipeType", propOrder = {
    "userId",
    "id",
    "title",
    "ingredient",
    "ner"
})
public class RecipeType {

    @XmlElement(required = true)
    protected String userId;
    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected List<IngredientType> ingredient;
    @XmlElement(name = "NER", required = true)
    protected List<NerType> ner;

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the ingredient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ingredient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIngredient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IngredientType }
     * 
     * 
     */
    public List<IngredientType> getIngredient() {
        if (ingredient == null) {
            ingredient = new ArrayList<IngredientType>();
        }
        return this.ingredient;
    }

    /**
     * Gets the value of the ner property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ner property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNER().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NerType }
     * 
     * 
     */
    public List<NerType> getNER() {
        if (ner == null) {
            ner = new ArrayList<NerType>();
        }
        return this.ner;
    }

}
