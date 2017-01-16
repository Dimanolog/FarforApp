package by.dimanolog.testappbalinasoft.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import by.dimanolog.testappbalinasoft.App;
import by.dimanolog.testappbalinasoft.R;
import by.dimanolog.testappbalinasoft.Util.ResourcesUtil;
import by.dimanolog.testappbalinasoft.beans.Category;

/**
 * Created by Dimanolog on 14.01.2017.
 */

public class CategoryChooser extends Fragment {
    private static final String TAG = CategoryChooser.class.getSimpleName();
    private static final int WIDTH_COLUMNS = 360;

    private RecyclerView mCategoryRecyclerView;
    private Map<String, String> mCategoryToImageNameMap;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategoryToImageNameMap = App.getCategoryToImageNameMap();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food_category_chooser, null);
        mCategoryRecyclerView = (RecyclerView) mCategoryRecyclerView
                .findViewById(R.id.fragment_category_choose_recycler_view);
        mCategoryRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mCategoryRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int pxl = mCategoryRecyclerView.getWidth();
                int nmbOfClmns = pxl / WIDTH_COLUMNS;
                mCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), nmbOfClmns));
                setupAdapter();
            }
        });

        return v;
    }

    private void setupAdapter() {
        if (isAdded()) {
            List<Category> categoryList=null;
            mCategoryRecyclerView.setAdapter(new CategoryAdapter(categoryList));
        }
    }

    private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mCategoryImage;
        private TextView mCategoryTextView;
        private Category mCategory;

        public CategoryHolder(View itemView) {
            super(itemView);
            mCategoryImage = (ImageView) itemView.findViewById(R.id.category_image_view);
            mCategoryTextView = (TextView) itemView.findViewById(R.id.category_text_view);
        }

        public void bindCategory(Category category) {
            mCategory = category;
            String categoryName = mCategory.getCategory();
            mCategoryTextView.setText(categoryName);


            String imageName = mCategoryToImageNameMap.get(categoryName);
            Drawable drawable;
            if (imageName != null) {
                drawable = ResourcesUtil.getDrawbleByName(imageName, getActivity());
            } else {
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.unknown, null);

            }
            mCategoryImage.setImageDrawable(drawable);


        }

        @Override
        public void onClick(View v) {


        }
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        private List<Category> mCategoryList;

        public CategoryAdapter(List<Category> categoryList) {
            mCategoryList = categoryList;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.food_category_list_item, parent, false);

            return new CategoryHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            Category category = mCategoryList.get(position);
            holder.bindCategory(category);

        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }

        public void setCategoryList(List<Category> categoryList) {
            mCategoryList = categoryList;
        }
    }


}
