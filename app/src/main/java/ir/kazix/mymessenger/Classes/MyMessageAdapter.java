package ir.kazix.mymessenger.Classes;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.kazix.mymessenger.ChatActivity;
import ir.kazix.mymessenger.R;

public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.ViewHolder> {

    public static ArrayList<MyMessage> localDataSet;

    public MyMessageAdapter() {

        Log.d("KA-MyMessageAdapter", "MyMessageAdapter");
        localDataSet = new ArrayList<>();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout backgroundView;
        //        private final CardView messageCardView;
        private final TextView messageTextView;
        private final TextView messageSrcID;
        private final CardView cardViewImageBackground;
        private final CardView cardViewMessageBackground;


        public CardView getCardViewMessageBackground() {
            return cardViewMessageBackground;
        }

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            Log.d("KA-MyMessageAdapter", "ViewHolder");

            backgroundView = view.findViewById(R.id.background_view);
            messageTextView = view.findViewById(R.id.message_text_view);
            cardViewImageBackground = view.findViewById(R.id.message_layout_image_back_gnd_color);
            cardViewMessageBackground = view.findViewById(R.id.message_layout_card_back_gnd_color);
            messageSrcID = view.findViewById(R.id.message_text_view_src_id);
        }

//        public CardView getMessageCardView() {
//            return messageCardView;
//        }


        public TextView getMessageSrcID() {
            return messageSrcID;
        }

        public TextView getMessageTextView() {
            return messageTextView;
        }

        public View getBackgroundView() {
            return backgroundView;
        }

        public CardView getCardViewImageBackground() {
            return cardViewImageBackground;
        }
    }

//    /**
//     * Initialize the dataset of the Adapter.
//     *
//     * @param dataSet String[] containing the data to populate views to be used
//     *                by RecyclerView.
//     */
//    public MyMessageAdapter(String[] dataSet) {
////        localDataSet = dataSet;
//    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Log.d("KA-MyMessageAdapter", "onCreateViewHolder");

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_message, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.d("KA-MyMessageAdapter", "onBindViewHolder");

        viewHolder.getMessageTextView().setText(localDataSet.get(position).getMessageText());
        viewHolder.getMessageSrcID().setText(localDataSet.get(position).getMessageSrcID());

        if (localDataSet.get(position).getMessageSrcID() != null && !localDataSet.get(position).getMessageSrcID().equals(ChatActivity.user.getEmail())) {

            viewHolder.getBackgroundView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            viewHolder.getCardViewMessageBackground().setCardBackgroundColor(Color.GREEN);
            viewHolder.getCardViewImageBackground().setCardBackgroundColor(Color.GREEN);

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        Log.d("KA-MyMessageAdapter", "getItemCount: " + localDataSet.size());
        return localDataSet.size();
    }
}
