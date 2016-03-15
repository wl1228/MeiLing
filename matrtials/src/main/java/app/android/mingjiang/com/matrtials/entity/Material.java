package app.android.mingjiang.com.matrtials.entity;

/**
 * Created by SunYi on 2015/12/22/0022.
 */
public class Material {

    //物料编码
    private String id;
    //物料名称
    private String name;
    //物料数量
    private int count;
    //安全库存
    private int safetyStock;
    //消耗比例
    private int proportion;

    //需求值，自己加的，用于请求去物料的数量单位
    private int need = 0;

    public Material(String id, String name, int count, int safetyStock, int proportion) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.safetyStock = safetyStock;
        this.proportion = proportion;
    }

    public Material() {
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(int safetyStock) {
        this.safetyStock = safetyStock;
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }
    //检查是否安全，若物料数量低于安全库存则视为不安全
    public boolean isSafe() {
        return count >= safetyStock;
    }
    //检查是否足够，若物料不够一次使用时就视为不够用
    public boolean isEnough() {
        return count >= proportion;
    }
    //使用一次的变化
    public void change() {
        if (count >= proportion) {
            count -= proportion;
        }
    }
    //增加需求值
    public int addNeed() {
        return ++need;
    }
    //减少需求值
    public int subNeed() {
        if (need > 0) {
            return --need;
        }
        return need;
    }

    /**
     * 增加物料数量
     * @param items  物料具体
     */
    public void add(int items) {
        count += items;
    }

    //自动算需求值，若物料数量低于安全库存，则设置默认需求值为1
    public void autoNeed() {
        if (!isSafe() && need == 0) {
            need++;
        }
    }

    /**
     * 物料到达，物料数量的变化
     * @param units 需求值一单位对应的物料数量，比如需求值的单位是箱，可能对应100个具体物料
     */
    public void arrivalOfGoods(int units) {
        count += need * units;
    }

    //打Log用
    @Override
    public String toString() {
        return "Material{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", safetyStock=" + safetyStock +
                ", proportion=" + proportion +
                ", need=" + need +
                '}';
    }
}
