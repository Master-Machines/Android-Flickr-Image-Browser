package com.dev.ds.coloradomountains.ui.picturelist;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.dev.ds.coloradomountains.R;
import com.dev.ds.coloradomountains.models.Photo;
import com.google.android.material.textfield.TextInputEditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class PictureListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SEARCH_BAR = 0;
    private static final int VIEW_TYPE_IMAGE_ROW = 1;


    public interface PhotoSelectedProtocol {
        public void photoSelected(Photo photo);
    }

    public interface SearchProtocol {
        public void searchApplied(String search);
    }

    private Photo[] dataSet;
    private Fragment parentFragment;
    private SearchBarViewHolder searchBarViewHolder;

    public PhotoSelectedProtocol photoSelectedDelegate;
    public SearchProtocol searchDelegate;

    public PictureListAdapter(Fragment parentFragment) {
        this.dataSet = new Photo[]{};
        this.parentFragment = parentFragment;
    }

    // Horizontal row of up to three images side by side
    private static class ImageRowViewHolder extends RecyclerView.ViewHolder {

        public ImageView leftImage;
        public ImageView middleImage;
        public ImageView rightImage;

        public ImageRowViewHolder(View v) {
            super(v);
            leftImage = v.findViewById(R.id.imageView1);
            middleImage = v.findViewById(R.id.imageView2);
            rightImage = v.findViewById(R.id.imageView3);
        }
    }

    private static class SearchBarViewHolder extends RecyclerView.ViewHolder {

        public TextInputEditText textInput;
        public ImageButton searchButton;

        public SearchBarViewHolder(View v) {
            super(v);
            textInput = v.findViewById(R.id.text_input);
            searchButton = v.findViewById(R.id.search_button);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        if (viewType == VIEW_TYPE_SEARCH_BAR) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_cell, parent, false);

            searchBarViewHolder = new SearchBarViewHolder(v);
            return (RecyclerView.ViewHolder) searchBarViewHolder;
        }

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.picture_list_cell, parent, false);

        ImageRowViewHolder viewHolder = new ImageRowViewHolder(v);
        return (RecyclerView.ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == VIEW_TYPE_SEARCH_BAR) {
            SearchBarViewHolder viewHolder = (SearchBarViewHolder) holder;
            viewHolder.searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (searchDelegate != null) {
                        searchDelegate.searchApplied(viewHolder.textInput.getText().toString());
                        viewHolder.textInput.clearFocus();
                    }
                }
            });

            // Handle when user clicks the Search button on the keyboard
            viewHolder.textInput.setOnEditorActionListener(
                    new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                                if (searchDelegate != null) {
                                    searchDelegate.searchApplied(viewHolder.textInput.getText().toString());
                                    viewHolder.textInput.clearFocus();
                                }
                                return true;
                            }
                            return false;
                        }
                    });
        } else {
            // Decrease position by one to account for search bar being at first positoin
            position --;
            setupImageRow((ImageRowViewHolder) holder, position);
        }
    }

    private void setupImageRow(ImageRowViewHolder viewHolder, int position) {
        RequestManager glide = Glide.with(parentFragment);
        int leftPosition = position * 3;
        if (leftPosition < dataSet.length) {
            glide.load(dataSet[leftPosition].media.photoUrl).apply(new RequestOptions().centerCrop()).into(viewHolder.leftImage);
            bindClickListener(viewHolder.leftImage, dataSet[leftPosition]);
        }

        int centerPosition = leftPosition + 1;
        if (centerPosition < dataSet.length) {
            glide.load(dataSet[centerPosition].media.photoUrl).apply(new RequestOptions().centerCrop()).into(viewHolder.middleImage);
            bindClickListener(viewHolder.middleImage, dataSet[centerPosition]);
        }

        int rightPosition = leftPosition + 2;
        if (rightPosition < dataSet.length) {
            glide.load(dataSet[rightPosition].media.photoUrl).apply(new RequestOptions().centerCrop()).into(viewHolder.rightImage);
            bindClickListener(viewHolder.rightImage, dataSet[rightPosition]);
        }
    }

    private void bindClickListener(View v, Photo p) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (photoSelectedDelegate != null) {
                    photoSelectedDelegate.photoSelected(p);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_SEARCH_BAR;
        }
        return VIEW_TYPE_IMAGE_ROW;
    }

    @Override
    public int getItemCount() {
        return  getNumberOfImageRows() + 1;
    }

    private int getNumberOfImageRows() {
        if (dataSet.length % 3 == 0) {
            return dataSet.length / 3;
        }
        return (dataSet.length / 3) + 1;
    }


    public void setDataSet(Photo[] dataSet) {
        this.dataSet = dataSet;
        this.notifyDataSetChanged();
    }

    public void focusOnSearchBar() {
        searchBarViewHolder.textInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) parentFragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
}
