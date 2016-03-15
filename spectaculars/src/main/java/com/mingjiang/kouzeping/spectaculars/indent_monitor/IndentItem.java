package com.mingjiang.kouzeping.spectaculars.indent_monitor;
/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 *
 * order_id:生产订单号
 * batch_id:物料编码
 * product_line:生产线
 * export_id:出口评审号
 * export_country:出口国家
 * product_id:成品代码
 * order_quantity:订单数量
 * expire_date:完成日期
 * is_customize：定制化订单
 * notes:备注
 * daily_plan:生产日计划
 * finish_number：完成数量
 *
 */
public class IndentItem {
    String order_id;
    String product_line;
    String export_id;
    String export_country;
    String product_id;
    String order_quantity;
    String expire_date;
    String is_customize;
    String notes;
    String finish_number;
    String batch_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getProduct_line() {
        return product_line;
    }

    public void setProduct_line(String product_line) {
        this.product_line = product_line;
    }

    public String getExport_id() {
        return export_id;
    }

    public void setExport_id(String export_id) {
        this.export_id = export_id;
    }

    public String getExport_country() {
        return export_country;
    }

    public void setExport_country(String export_country) {
        this.export_country = export_country;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(String order_quantity) {
        this.order_quantity = order_quantity;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getIs_customize() {
        return is_customize;
    }

    public void setIs_customize(String is_customize) {
        this.is_customize = is_customize;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFinish_number() {
        return finish_number;
    }

    public void setFinish_number(String finish_number) {
        this.finish_number = finish_number;
    }
}
