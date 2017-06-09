package com.epicodus.quibit.models;

import org.parceler.Parcel;

@Parcel
public class Item {
     String mItemId;
     String mName;
     String mMsrp;
     String mSalePrice;
     String mDescription;
     String mThumbnailImage;
     String mMediumImage;
     String mLargeImage;
     String mRating;
     String mPurchaseLink;

    public Item(){
    }

    public Item(String mItemId, String mName, String mMsrp, String mSalePrice, String mDescription, String mThumbnailImage, String mMediumImage, String mLargeImage, String mRating, String mPurchaseLink) {
        this.mItemId = mItemId;
        this.mName = mName;
        this.mMsrp = mMsrp;
        this.mSalePrice = mSalePrice;
        this.mDescription = mDescription;
        this.mThumbnailImage = mThumbnailImage;
        this.mMediumImage = mMediumImage;
        this.mLargeImage = mLargeImage;
        this.mRating = mRating;
        this.mPurchaseLink = mPurchaseLink;
    }

    public String getItemId() {
        return mItemId;
    }

    public String getName() {
        return mName;
    }

    public String getMsrp() {
        return mMsrp;
    }

    public String getSalePrice() {
        return mSalePrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getThumbnailImage() {
        return mThumbnailImage;
    }

    public String getMediumImage() {
        return mMediumImage;
    }

    public String getLargeImage() {
        return mLargeImage;
    }

    public String getRating() {
        return mRating;
    }

    public String getPurchaseLink() {
        return mPurchaseLink;
    }
}
