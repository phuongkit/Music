package com.example.myapplication.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Module.AccountType;
import com.example.myapplication.Module.Listeners.OnLoadMoreListener;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import java.util.ArrayList;

public class LoginDialog extends Dialog {
    ImageButton imgBtnBack, imgBtnInfo, imgBtnLoadMore;
    TextView txtTittleLogin, txtInfoTittleLogin, txtPolicy;
    Button btnOtherLogin;
    boolean login = false;
    Activity context;

    RecyclerView recyclerViewAccountType;
    ArrayList<AccountType> accountTypes;
    ArrayList<AccountType> accountTypess;
    AccountTypeAdapter accountTypeAdapter;
//    AccountTypeAdapter

    public LoginDialog(Activity context) {
        super(context);
        this.context = context;
        setContentView(R.layout.login_dialog_item);
        addControls();
        init();
        addEvents();
        ViewGroup.LayoutParams params = getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1.00);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.90);
        getWindow().setLayout(width, height);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
    }

    private void init() {
        if (login) {
            txtTittleLogin.setText("Đăng nhập vào " + context.getString(R.string.app_name));
            txtInfoTittleLogin.setText("Quản lý tài khoản, kiểm tra thông báo, tương tác, bình luân trên các bài hát, v.v.");
            txtPolicy.setText("");

            Link log_Link = new Link("Đăng ký")
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true);                                           // optional, defaults to false
//                    .setOnLongClickListener(new Link.OnLongClickListener() {
//                        @Override
//                        public void onLongClick(String clickedText) {
//                            // long clicked
//                        }
//                    })
            btnOtherLogin.setText("Bạn không có tài khoản?Đăng ký");
            LinkBuilder.on(btnOtherLogin).addLink(log_Link).build();
        } else {
            txtTittleLogin.setText("Đăng ký " + context.getString(R.string.app_name));
            txtInfoTittleLogin.setText("Tạo hồ sơ, theo dõi các tài khoản khác, nghe nhạc của chính bạn, v.v.");
            Link terms_Of_Service_Link = new Link("Điều khoản dịch vụ")
                    .setTextColor(Color.parseColor("#000000"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                              // optional, defaults to false
                    .setOnClickListener(new Link.OnClickListener() {
                        @Override
                        public void onClick(String clickedText) {
                            // single clicked
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/terms?hl=vi&amp;gl=VN"));
                            context.startActivity(browserIntent);
                        }
                    });
            Link privacy_Policy_Link = new Link("Chính sách Quyền riêng tư")
                    .setTextColor(Color.parseColor("#000000"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                              // optional, defaults to false
                    .setOnClickListener(new Link.OnClickListener() {
                        @Override
                        public void onClick(String clickedText) {
                            // single clicked
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy?hl=vi&amp;gl=VN"));
                            context.startActivity(browserIntent);
                        }
                    });

            txtPolicy.setText("Bằng cách tiếp tục, bạn đồng ý với Điều khoản dịch vụ của chúng tôi và thừa nhận rằng bạn đã đọc Chính sách Quyền riêng tư để tìm hiểu cách chúng tôi thu thập, sử dụng và chia sẽ dữ liệu của bạn.");
            LinkBuilder.on(txtPolicy).addLink(terms_Of_Service_Link).build();
            LinkBuilder.on(txtPolicy).addLink(privacy_Policy_Link).build();

            txtPolicy.setMovementMethod(LinkMovementMethod.getInstance());

            Link log_Link = new Link("Đăng nhập")
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true);                                            // optional, defaults to false
//                    .setOnLongClickListener(new Link.OnLongClickListener() {
//                        @Override
//                        public void onLongClick(String clickedText) {
//                            // long clicked
//                        }
//                    })
            btnOtherLogin.setText("Bạn đã có tài khoản?Đăng nhập");
            LinkBuilder.on(btnOtherLogin).addLink(log_Link).build();
        }
    }

    private void addEvents() {
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        accountTypeAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                accountTypess.add(null);
                accountTypeAdapter.notifyItemInserted(accountTypess.size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        accountTypess.remove(accountTypess.size() - 1);
                        accountTypeAdapter.notifyItemRemoved(accountTypess.size());
                        int index = accountTypess.size();
//                        int end = index + 5 > accountTypes.size() ? accountTypes.size() : index + 5;
                        int end = accountTypes.size();
                        for (int i = index; i < end; i++) {
                            accountTypess.add(accountTypes.get(i));
                        }
                        accountTypeAdapter.notifyDataSetChanged();
                        accountTypeAdapter.isLoading = false;
                    }
                }, 500);
            }
        });
        btnOtherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = !login;
                init();
            }
        });
    }

    private void addControls() {
        imgBtnBack = findViewById(R.id.imgBtnBack);
        imgBtnInfo = findViewById(R.id.imgBtnInfo);
        recyclerViewAccountType = findViewById(R.id.recyclerViewAccountType);
        txtTittleLogin = findViewById(R.id.txtTitleLogin);
        txtInfoTittleLogin = findViewById(R.id.txtInfoTittleLogin);
        imgBtnLoadMore = findViewById(R.id.imgBtnLoadMore);
        txtPolicy = findViewById(R.id.txtPolicy);
        btnOtherLogin = findViewById(R.id.btnOtherLogin);
        setTitle("Login");
        setCanceledOnTouchOutside(false);

        recyclerViewAccountType.setLayoutManager(new LinearLayoutManager(context));
        accountTypeAdapter = new AccountTypeAdapter();
        recyclerViewAccountType.setAdapter(accountTypeAdapter);
        accountTypes = new ArrayList<>();
        accountTypess = new ArrayList<>();
        accountTypes.add(new AccountType(1 + "", "Sử dụng với email", R.drawable.ic_user1));
        accountTypes.add(new AccountType(2 + "", "Tiếp tục với Google", R.drawable.ic_google));
        accountTypes.add(new AccountType(3 + "", "Tiếp tục với facebook", R.drawable.ic_facebook));
        accountTypes.add(new AccountType(4 + "", "Tiếp tục với Twitter", R.drawable.ic_twitter));
        accountTypes.add(new AccountType(5 + "", "Tiếp tục với Instagram", R.drawable.ic_instagram));
        accountTypes.add(new AccountType(6 + "", "Tiếp tục với Line", R.drawable.ic_line));
        int index = 0;
        int end = 3;
        for (int i = index; i < end; i++) {
            accountTypess.add(accountTypes.get(i));
        }
        if (accountTypes.size() < 4) {
            imgBtnLoadMore.setVisibility(View.GONE);
        }
    }

    class AccountTypeAdapter extends RecyclerView.Adapter<AccountTypeViewHolder> {

        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;
        OnLoadMoreListener onLoadMoreListener;
        public boolean isLoading = false;
        int visibleThrehold = 3;
        int lastVisibleItem;
        int totalItem;

        public OnLoadMoreListener getOnLoadMoreListener() {
            return onLoadMoreListener;
        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }

        public AccountTypeAdapter() {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewAccountType.getLayoutManager();
            imgBtnLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageButton btn = (ImageButton) view;
                    btn.setVisibility(View.GONE);
                    totalItem = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!isLoading && totalItem <= (lastVisibleItem + visibleThrehold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
//            recyclerViewAccountType.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    totalItem = linearLayoutManager.getItemCount();
//                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                    if (!isLoading && totalItem <= (lastVisibleItem + visibleThrehold)) {
//                        if (onLoadMoreListener != null) {
//                            onLoadMoreListener.onLoadMore();
//                        }
//                        isLoading = true;
//                    }
//
//                }
//            });
        }

        @NonNull
        @Override
        public AccountTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View accountTypeView = LayoutInflater.from(context).inflate(R.layout.account_type_item, parent, false);
            return new AccountTypeViewHolder(accountTypeView);
//            return null;
        }

//        @NonNull
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            if (viewType == VIEW_TYPE_ITEM) {
//                View accountTypeView = LayoutInflater.from(context).inflate(R.layout.account_type_item, parent, false);
//                return new AccountTypeViewHolder(accountTypeView);
//            }
//            if (viewType == VIEW_TYPE_LOADING) {
//                View loadingView = LayoutInflater.from(context).inflate(R.layout.loadingitem, parent, false);
//                return new LoadingViewHolder(loadingView);
//            }
//            return null;
//        }

        @Override
        public void onBindViewHolder(@NonNull AccountTypeViewHolder holder, int position) {
            AccountType accountType = accountTypess.get(position);
            AccountTypeViewHolder accountTypeViewHolder = (AccountTypeViewHolder) holder;
            accountTypeViewHolder.imgAccountType.setImageResource(accountType.getIdIcon());
            accountTypeViewHolder.txtAccountType.setText(accountType.getName());

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    if (isLongClick) {
//                        Toast.makeText(context, "Long Click: "+listData.get(position), Toast.LENGTH_SHORT).show();
                    } else {
                        switch (accountType.getIdAccountType()) {
                            case "1":
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.putExtra("login", login);
                                context.startActivity(intent);
                                break;
                        }
                    }
                }
            });
        }

//        @Override
//        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//            if (holder instanceof AccountTypeViewHolder) {
//                AccountType accountType = accountTypess.get(position);
//                AccountTypeViewHolder accountTypeViewHolder = (AccountTypeViewHolder) holder;
//                accountTypeViewHolder.imgAccountType.setImageResource(accountType.getIdIcon());
//                accountTypeViewHolder.txtAccountType.setText(accountType.getName());
//            }
//            else if (holder instanceof LoadingViewHolder) {
//                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
//                loadingViewHolder.progressBar.setIndeterminate(true);
//            }
//
//        }


        @Override
        public int getItemCount() {
            return accountTypess == null ? 0 : accountTypess.size();
        }

        @Override
        public int getItemViewType(int position) {
            return accountTypess.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    static class AccountTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgAccountType;
        public TextView txtAccountType;

        private ItemClickListener itemClickListener;

        public AccountTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAccountType = itemView.findViewById(R.id.imgAccountType);
            txtAccountType = itemView.findViewById(R.id.txtAccountType);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return false;
        }
    }
}
