package app.android.mingjiang.com.matrtials.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.android.mingjiang.com.matrtials.R;
import app.android.mingjiang.com.matrtials.entity.Material;

/**
 * Created by SunYi on 2015/12/22/0022.
 */
public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.ViewHolder> implements View.OnClickListener {
    public ArrayList<Material> materials = null;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    public MaterialAdapter(ArrayList<Material> materials) {
        this.materials = materials;
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Material data);
    }


    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //获得对应物料
        Material m = materials.get(position);

        viewHolder.itemView.setTag(m);
        viewHolder.materialId.setText(m.getId());
        viewHolder.materialName.setText(m.getName());
        viewHolder.materialCount.setText(String.valueOf(m.getCount()));
        viewHolder.safeStock.setText(String.valueOf(m.getSafetyStock()));
        viewHolder.proportion.setText(String.valueOf(m.getProportion()));

        //判断当前物料状态，显示不同效果
        if (m.isSafe()) {
            viewHolder.layout.setBackgroundColor(Color.WHITE);
            viewHolder.getMore.setText(R.string.enough);
            viewHolder.getMore.setTextColor(Color.GREEN);
        } else if (!m.isEnough()) {
            viewHolder.layout.setBackgroundColor(Color.RED);
            viewHolder.getMore.setText(R.string.get_more);
            viewHolder.getMore.setTextColor(Color.YELLOW);
        } else {
            viewHolder.layout.setBackgroundColor(Color.rgb(255, 200, 100));
            viewHolder.getMore.setText(R.string.get_more);
            viewHolder.getMore.setTextColor(Color.BLUE);
        }

    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return materials.size();
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(view, (Material) view.getTag());
        }
    }


    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView materialId;
        TextView materialName;
        TextView materialCount;
        TextView safeStock;
        TextView proportion;
        TextView getMore;
        LinearLayout layout;

        public ViewHolder(View view) {
            super(view);
            materialId = (TextView) view.findViewById(R.id.material_id);
            materialName = (TextView) view.findViewById(R.id.material_name);
            materialCount = (TextView) view.findViewById(R.id.material_count);
            safeStock = (TextView) view.findViewById(R.id.safe_stock);
            proportion = (TextView) view.findViewById(R.id.proportion);
            getMore = (TextView) view.findViewById(R.id.get_more);
            layout = (LinearLayout) view.findViewById(R.id.item_layout);
        }
    }

}