package com.epicodus.quibit.models;


public class Item {
    private String mItemId;
    private String mName;
    private String mMsrp;
    private String mSalePrice;
    private String mDescription;
    private String mThumbnailImage;
    private String mMediumImage;
    private String mLargeImage;
    private String mRating;
    private String mPurchaseLink;

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
}