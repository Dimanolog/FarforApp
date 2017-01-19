package by.dimanolog.testappbalinasoft.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import by.dimanolog.testappbalinasoft.R;
import by.dimanolog.testappbalinasoft.beans.Category;
import by.dimanolog.testappbalinasoft.beans.Offer;
import by.dimanolog.testappbalinasoft.beans.Param;
import by.dimanolog.testappbalinasoft.services.ImageDownloader;
import by.dimanolog.testappbalinasoft.services.UfaFarforDataProvider;
import by.dimanolog.testappbalinasoft.util.ParamNames;

/**
 * Created by Dimanolog on 17.01.2017.
 */

public class OfferListFragment extends Fragment {

    private static String ARG_CATEGORY="category";
    private RecyclerView mOfferListRecyclerView;
    private List<Offer> mOfferList;
    private Category mCategory;
    private OfferListFragmentCallback mOfferListFragmentCallback;

    interface OfferListFragmentCallback{
        void onOfferItemClick(Offer offer);
    }

    public static OfferListFragment newInstance(@Nullable Category  category) {
        Bundle args = new Bundle();
        OfferListFragment fragment = new OfferListFragment();
        if(category!=null) {
            args.putSerializable(ARG_CATEGORY, category);
            fragment.setArguments(args);
        }
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory=(Category)getArguments().getSerializable(ARG_CATEGORY);
        mOfferList=UfaFarforDataProvider.getInstance(getActivity()).getOfferInCategory(mCategory);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_offers_list, null);
        mOfferListRecyclerView = (RecyclerView) mOfferListRecyclerView
                .findViewById(R.id.fragment_category_choose_recycler_view);
        mOfferListRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));

        setupAdapter();

        return v;
    }

    private void setupAdapter(){
        if(isAdded()){
            mOfferListRecyclerView.setAdapter(new OfferAdapter(mOfferList));
        }
    }


    private class OfferHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mOfferImageView;
        private TextView mOfferNameTxtView;
        private TextView mOfferWeightTxtView;
        private TextView mOfferPriceTxtView;

        private Offer mOffer;


        public OfferHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mOfferImageView = (ImageView)
                    itemView.findViewById(R.id.offer_image_view);
            mOfferNameTxtView = (TextView)
                    itemView.findViewById(R.id.offer_list_item_offer_name_txt);
            mOfferWeightTxtView = (TextView)
                    itemView.findViewById(R.id.offer_list_item_weight_txt);
            mOfferPriceTxtView=(TextView)itemView.findViewById(R.id.offer_list_item_price_txt);

        }

        public void bindOffer(Offer offer) {
            mOffer = offer;
            mOfferNameTxtView.setText(mOffer.getName());
            String price=String.valueOf(mOffer.getPrice());
            mOfferPriceTxtView.setText(price);
            Param weightParam=mOffer.getParamsByName(ParamNames.WEIGHT);
            mOfferWeightTxtView.setText(weightParam.toString());

        }

        public void bindDrawable(Drawable drawable)
        {
            mOfferImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            mOfferListFragmentCallback.onOfferItemClick(mOffer);

        }
    }

    private class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        private List<Offer> mOfferList;
        private Drawable mDefaultDrawable;

        public OfferAdapter(List<Offer> offerList) {
           mOfferList=offerList;
           mDefaultDrawable= ResourcesCompat.getDrawable(getResources(),R.drawable.unknown,null);
    }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.offers_list_item, parent, false);

            return new OfferHolder(view);
        }

        @Override
        public void onBindViewHolder(final OfferHolder holder, int position) {
            Offer offer = mOfferList.get(position);
            holder.bindOffer(offer);
            holder.bindDrawable(mDefaultDrawable);
            if(offer.getPictureUrl()!=null) {
                ImageDownloader imageDownloader = ImageDownloader.getInstance(getActivity());
                imageDownloader.downloadImage(offer.getUrl(), new ImageDownloader.ImageDownloadedCallback() {
                    @Override
                    public void onImageDowloaded(Bitmap bitmap) {
                        if(bitmap!=null){
                            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                            holder.bindDrawable(drawable);
                        }
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return mOfferList.size();

        }

    }
}
