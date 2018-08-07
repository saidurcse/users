package cmed.com.userdata.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cmed.com.userdata.R;
import cmed.com.userdata.realm.dao.UserInfoDao;
import cmed.com.userdata.retrofit.model.User;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class userAdapter extends RealmRecyclerViewAdapter<User, userAdapter.UserViewHolder> {


    public userAdapter(RealmResults<User> users) {
        super(users, true);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userinfo_grid_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        final User user = getItem(position);
        if (user != null) {
            holder.bind(user);
        }

    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        final Context context;
        public CircleImageView imageBackground;
        public TextView Name;
        public TextView Phone;


        public UserViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();

            imageBackground = itemView.findViewById(R.id.image_background);
            Name = itemView.findViewById(R.id.Name);
            Phone = itemView.findViewById(R.id.Phone);
        }

        public void bind(final User user) {

            Name.setText(user.getFirstName() + " " + user.getLastName());
            UserInfoDao userInfoDao = new UserInfoDao();
            String mobile = userInfoDao.getPhone(user.getId());
            Phone.setText(mobile);

            if (user.getImageByte() != null) {

                byte[] decodedString = user.getImageByte();

                Glide.with(context)
                        .load(decodedString)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageBackground);
            }

        }
    }
}
