package by.dimanolog.farfo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import by.dimanolog.farfo.R;
import by.dimanolog.farfo.model.Offer;
import by.dimanolog.farfo.model.Param;

/**
 * Created by Dimanolog on 23.01.2017.
 */

public class OfferFragment extends Fragment {
    private static final String ARG_OFFER = "offer";
    private TextView mOfferTitle;
    private TextView mOfferDescription;
    private TextView mOfferPrice;
    private TextView mOfferParams;

    private ImageView mOfferImageView;

    private Offer mOffer;


    public static OfferFragment newInstance(Offer offer) {
        Bundle args = new Bundle();
        OfferFragment fragment = new OfferFragment();
        if (offer != null) {
            args.putSerializable(ARG_OFFER, offer);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(ARG_OFFER)) {
                mOffer = (Offer) bundle.getSerializable(ARG_OFFER);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offer, container, false);
        mOfferTitle = (TextView) view.findViewById(R.id.fragment_offer_name);
        mOfferDescription = (TextView) view.findViewById(R.id.fragment_offer_description);
        mOfferPrice = (TextView) view.findViewById(R.id.fragment_offer_price);
        mOfferParams = (TextView) view.findViewById(R.id.fragment_offer_param);
        mOfferImageView = (ImageView) view.findViewById(R.id.fragment_offer_image);

        fillOffer();

        return view;
    }

    private void fillOffer() {
        if (mOffer != null) {
            mOfferTitle.setText(mOffer.getName());
            mOfferDescription.setText(mOffer.getDescription());
            String price = String.valueOf(mOffer.getPrice());
            mOfferPrice.setText(price);
            List<Param> paramList = mOffer.getParamList();
            String lineSeparator = System.getProperty("line.separator");
            for (Param param : paramList) {
                mOfferParams.append(param.toString());
                mOfferParams.append(lineSeparator);
            }
            if (mOffer.getPictureUrl() != null) {
                Picasso.with(getActivity())
                        .load(mOffer.getPictureUrl())
                        .placeholder(R.drawable.unknown)
                        .into(mOfferImageView);
            }
        }
    }
}
