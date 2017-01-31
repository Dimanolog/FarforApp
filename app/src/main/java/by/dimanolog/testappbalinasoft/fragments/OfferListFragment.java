package by.dimanolog.testappbalinasoft.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import by.dimanolog.testappbalinasoft.R;
import by.dimanolog.testappbalinasoft.model.Category;
import by.dimanolog.testappbalinasoft.model.Offer;
import by.dimanolog.testappbalinasoft.model.Param;
import by.dimanolog.testappbalinasoft.services.FarforDataProvider;
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

   public interface OfferListFragmentCallback{
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
    public void onAttach(Context context) {
        super.onAttach(context);
        mOfferListFragmentCallback=(OfferListFragmentCallback)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.containsKey(ARG_CATEGORY)){
                mCategory=(Category)bundle.getSerializable(ARG_CATEGORY);
            }
        }

        mOfferList= FarforDataProvider.getInstance(getActivity()).getOfferInCategory(mCategory);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_offers_list, null);
        mOfferListRecyclerView = (RecyclerView)v
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
            if(weightParam!=null) {
                mOfferWeightTxtView.setText(weightParam.toString());
            }
            Picasso.with(getActivity())
                    .load(offer.getPictureUrl())
                    .placeholder(R.drawable.unknow_mini)
                    .error(R.drawable.unknow_mini)
                    .into(mOfferImageView);
        }

        @Override
        public void onClick(View v) {
            mOfferListFragmentCallback.onOfferItemClick(mOffer);

        }
    }

    private class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {
        private List<Offer> mOfferList;


        public OfferAdapter(List<Offer> offerList) {
           mOfferList=offerList;

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

        }

        @Override
        public int getItemCount() {
            return mOfferList.size();

        }

    }
}
