package com.gotechno.technovanza13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.gotechno.technovanza13.ImageTextListAdapter.ImageTextListItem;
import java.util.ArrayList;
import java.util.List;
 
/**
 * An {@link ArrayAdapter} that displays pairs of images and text.
 * 
 * @author lukehorvat
 * 
 */
public class ImageTextListAdapter extends ArrayAdapter<ImageTextListItem>
{
    public ImageTextListAdapter(Context context)
    {
        this(context, new ArrayList<ImageTextListItem>());
    }
 
    public ImageTextListAdapter(Context context, List<ImageTextListItem> items)
    {
        super(context, R.layout.list_item_image_text, items);
    }
 
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        // apply the image text list item layout to the current list item
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >=14 ) 
            view = inflater.inflate(R.layout.list_item_image_text, parent, false);
            else view = inflater.inflate(R.layout.list_item_image_text_lessv14, parent, false);
        }
 
        // set the image and text for the current list item
        ImageTextListItem listItem = getItem(position);
 
        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(listItem.getImageResourceId());
 
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(listItem.getTextResourceId());
 
        return view;
    }
 
    public static class ImageTextListItem
    {
        private int mTextResourceId;
        private int mImageResourceId;
 
        public ImageTextListItem(int textResourceId, int imageResourceId)
        {
            setTextResourceId(textResourceId);
            setImageResourceId(imageResourceId);
        }
 
        public int getTextResourceId()
        {
            return mTextResourceId;
        }
 
        public void setTextResourceId(int textResourceId)
        {
            mTextResourceId = textResourceId;
        }
 
        public int getImageResourceId()
        {
            return mImageResourceId;
        }
 
        public void setImageResourceId(int imageResourceId)
        {
            mImageResourceId = imageResourceId;
        }
    }
}