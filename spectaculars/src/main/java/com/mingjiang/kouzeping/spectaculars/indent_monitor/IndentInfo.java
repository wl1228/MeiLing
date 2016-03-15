package com.mingjiang.kouzeping.spectaculars.indent_monitor;

import java.util.List;

/**
 * Created by kouzeping on 2016/2/29.
 * email：kouzeping@shmingjiang.org.cn
 */
public class IndentInfo {
//            "result_size": 2,
//                "result": [
//            {
//                "product_line": "3333", "product_id": "135", "order_id": 1002, "notes": false, "export_country": false, "is_customize": true,
//                    "order_quantity": 55526, "expire_date": "2016-02-02", "batch_id": "1003", "export_id": "xyz", "id": 6, "finish_number": 232
    String result_size;
    List<IndentItem> result;

    public String getResult_size() {
        return result_size;
    }

    public void setResult_size(String result_size) {
        this.result_size = result_size;
    }

    public List<IndentItem> getResult() {
        return result;
    }

    public void setResult(List<IndentItem> result) {
        this.result = result;
    }
}
