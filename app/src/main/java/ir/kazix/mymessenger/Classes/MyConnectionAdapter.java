package ir.kazix.mymessenger.Classes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.kazix.mymessenger.ChatActivity;
import ir.kazix.mymessenger.ChatFragmentRoom;
import ir.kazix.mymessenger.R;

public class MyConnectionAdapter extends RecyclerView.Adapter<MyConnectionAdapter.ViewHolder> {

    public static ArrayList<Connection> localDataSet;

    public MyConnectionAdapter() {
        localDataSet = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView clientId;
        private final TextView socketId;
        private final TextView remotePort;
        private final TextView remoteAddress;
        private final TextView userAgent;
        private final TextView handshakeTime;
        private final CardView connectionView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            Log.d("KA-MyConnectionAdapter", "ViewHolder");
            clientId = view.findViewById(R.id.connection_layout_client_id);
            socketId = view.findViewById(R.id.connection_layout_socket_id);
            remotePort = view.findViewById(R.id.connection_layout_remote_port);
            remoteAddress = view.findViewById(R.id.connection_layout_remote_address);
            handshakeTime = view.findViewById(R.id.connection_layout_handshake_time);
            userAgent = view.findViewById(R.id.connection_layout_user_agent);
            connectionView = view.findViewById(R.id.connection_card_layout);
        }

        public TextView getClientId() {
            return clientId;
        }

        public TextView getSocketId() {
            return socketId;
        }

        public TextView getRemotePort() {
            return remotePort;
        }

        public TextView getRemoteAddress() {
            return remoteAddress;
        }

        public TextView getUserAgent() {
            return userAgent;
        }

        public TextView getHandshakeTime() {
            return handshakeTime;
        }

        public CardView getConnectionView() {
            return connectionView;
        }

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyConnectionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Log.d("KA-MyConnectionAdapter", "onCreateViewHolder");

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_connection, viewGroup, false);

        return new MyConnectionAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyConnectionAdapter.ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        Log.d("KA-MyConnectionAdapter", "onBindViewHolder");

        viewHolder.getConnectionView().setOnClickListener(view -> {

            ChatFragmentRoom.receiverId = localDataSet.get(viewHolder.getAdapterPosition()).getClientId();

            ChatActivity.fragmentManager
                    .beginTransaction()
                    .replace(R.id.activity_chat_fragment, ChatFragmentRoom.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name") // name can be null
                    .commit();
        });

        viewHolder.getClientId().setText(localDataSet.get(viewHolder.getAdapterPosition()).getClientId());
        viewHolder.getSocketId().setText(localDataSet.get(viewHolder.getAdapterPosition()).getSocketId());
        viewHolder.getRemotePort().setText(localDataSet.get(viewHolder.getAdapterPosition()).getRemotePort());
        viewHolder.getRemoteAddress().setText(localDataSet.get(viewHolder.getAdapterPosition()).getRemoteAddress());
        viewHolder.getUserAgent().setText(localDataSet.get(viewHolder.getAdapterPosition()).getUserAgent());
        viewHolder.getHandshakeTime().setText(localDataSet.get(viewHolder.getAdapterPosition()).getHandshakeTime());

    } // end onBindViewHolder

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        Log.d("KA-MyConnectionAdapter", "getItemCount: " + localDataSet.size());
        return localDataSet.size();
    }
}
