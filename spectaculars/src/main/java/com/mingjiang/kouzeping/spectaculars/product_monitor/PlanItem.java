package com.mingjiang.kouzeping.spectaculars.product_monitor;

/**
 * Created by kouzeping on 2016/2/2.
 * email：kouzeping@shmingjiang.org.cn
 * [{"section_gd0020": 0, "section_gd0010": 2, "order_id": "11111111", "id": 6, "plan_quantity": 110, "store_quantity": 0}]
 */
public class PlanItem {
    int section_gd0020;
    int section_gd0010;
    String order_id;
    String id;
    int plan_quantity;
    int store_quantity;

    public int getSection_gd0020() {
        return section_gd0020;
    }

    public void setSection_gd0020(int section_gd0020) {
        this.section_gd0020 = section_gd0020;
    }

    public int getSection_gd0010() {
        return section_gd0010;
    }

    public void setSection_gd0010(int section_gd0010) {
        this.section_gd0010 = section_gd0010;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlan_quantity() {
        return plan_quantity;
    }

    public void setPlan_quantity(int plan_quantity) {
        this.plan_quantity = plan_quantity;
    }

    public int getStore_quantity() {
        return store_quantity;
    }

    public void setStore_quantity(int store_quantity) {
        this.store_quantity = store_quantity;
    }
}
