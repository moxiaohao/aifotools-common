//package io.aifo.example.ui;
//
//import android.view.View;
//
//import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.foryou.coreui.recycler.MultipleItemEntity;
//import com.foryou.coreui.recycler.MutipleItemType;
//
//import java.util.List;
//
//import io.aifo.example.R;
//
//
//public class InfoAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity, BaseViewHolder> implements View.OnClickListener {
//    /**
//     * Same as QuickAdapter#QuickAdapter(Context,int) but with
//     * some initialization data.
//     *
//     * @param data A new list is created out of this one to avoid mutable list
//     */
//
//
//    public InfoAdapter(List<MultipleItemEntity> data) {
//        super(data);
//        addItemType(MutipleItemType.TEXT, R.layout.item_main);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, MultipleItemEntity item) {
//
//        switch (item.getItemType()) {
//
//            case MutipleItemType.TEXT:
//                break;
//
//        }
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//
//    }
//}
