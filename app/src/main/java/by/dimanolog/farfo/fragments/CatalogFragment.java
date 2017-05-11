package by.dimanolog.farfo.fragments;

import android.content.Context;
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

import by.dimanolog.farfo.App;
import by.dimanolog.farfo.R;
import by.dimanolog.farfo.model.Category;
import by.dimanolog.farfo.services.FarforDataProvider;
import by.dimanolog.farfo.utilities.ResourcesUtil;

/**
 * Created by Dimanolog on 14.01.2017.
 */

public class CatalogFragment extends Fragment {
    private static final String TAG = CatalogFragment.class.getSimpleName();
    private static final String ARG_CURRENT_CATEGORY = "current_category";
    private static final int WIDTH_COLUMNS = 300;

    private RecyclerView mCategoryRecyclerView;
    private Map<String, String> mCategoryToImageNameMap;
    private Category mCurrentCategory;
    private CategoryChooserFragmentCallback mCategoryChooserFragmentCallback;

    public interface CategoryChooserFragmentCallback {
        void onCategoryItemClick(Category category);
    }

    public static CatalogFragment newInstance(@Nullable Category currentCategory) {

        CatalogFragment fragment = new CatalogFragment();

        Bundle args = new Bundle();
        if (currentCategory != null) {
            args.putSerializable(ARG_CURRENT_CATEGORY, currentCategory);
        }
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryToImageNameMap = App.getCategoryToImageNameMap();
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(ARG_CURRENT_CATEGORY)) {
                mCurrentCategory = (Category) getArguments().getSerializable(ARG_CURRENT_CATEGORY);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCategoryChooserFragmentCallback = (CategoryChooserFragmentCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category_list, null);
        mCategoryRecyclerView = (RecyclerView) v
                .findViewById(R.id.fragment_category_choose_recycler_view);
        mCategoryRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mCategoryRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int pxl = mCategoryRecyclerView.getWidth();
                int nmbOfClmns = pxl / WIDTH_COLUMNS;
                mCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), nmbOfClmns));
            }
        });
        setupAdapter();

        return v;
    }


    private void setupAdapter() {
        if (isAdded()) {
            List<Category> categoryList = FarforDataProvider
                    .getInstance(getActivity())
                    .getCategorysList();
            mCategoryRecyclerView.setAdapter(new CategoryAdapter(categoryList));
        }
    }

    private class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mCategoryImage;
        private TextView mCategoryTextView;
        private View mCategorySelector;
        private Category mCategory;


        public CategoryHolder(View itemView) {
            super(itemView);
            mCategoryImage = (ImageView) itemView.findViewById(R.id.category_image_view);
            mCategoryTextView = (TextView) itemView.findViewById(R.id.category_text_view);
            mCategorySelector = itemView.findViewById(R.id.category_selector);
            itemView.setOnClickListener(this);
        }

        public void bindCategory(Category category) {
            mCategory = category;
            String categoryName = mCategory.getCategory();
            mCategoryTextView.setText(categoryName);
            checkSelected();

        }

        public void bindDrawable(Drawable drawable) {
            mCategoryImage.setImageDrawable(drawable);
        }


        @Override
        public void onClick(View v) {
            mCategoryChooserFragmentCallback.onCategoryItemClick(mCategory);
            mCurrentCategory = mCategory;
            getArguments().putSerializable(ARG_CURRENT_CATEGORY, mCurrentCategory);

        }

        private void checkSelected() {
            if (mCategory.equals(mCurrentCategory))
                mCategorySelector.setVisibility(View.VISIBLE);
        }

    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

        private List<Category> mCategoryList;
        Drawable mUnknownCategoryDrw;

        public CategoryAdapter(List<Category> categoryList) {
            mCategoryList = categoryList;
            mUnknownCategoryDrw = ResourcesCompat.getDrawable(getResources(), R.drawable.unknown_mini, null);
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
            String categoryName = category.getCategory();
            String imageName = mCategoryToImageNameMap.get(categoryName);
            Drawable drawable;
            if (imageName != null) {
                drawable = ResourcesUtil.getDrawbleByName(imageName, getActivity());

            } else {
                drawable = mUnknownCategoryDrw;

            }
            holder.bindDrawable(drawable);
        }

        @Override
        public int getItemCount() {
            return mCategoryList.size();
        }

    }
}
