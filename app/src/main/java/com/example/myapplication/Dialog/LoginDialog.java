package com.example.myapplication.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.LoginActivity;
import com.example.myapplication.Generic.Beans.AccountType;
import com.example.myapplication.Generic.Listeners.OnLoadMoreListener;
import com.example.myapplication.R;
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

    public LoginDialog(Activity context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_login);
        addControls();
        init();
        addEvents();
//        ViewGroup.LayoutParams params = getWindow().getAttributes();
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1.00);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.90);
        getWindow().setLayout(width, height);
    }

    public String getString(int resId) {
        return context.getString(resId);
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (login) {
            txtTittleLogin.setText((getString(R.string.strHeaderSignIn) + " " + getString(R.string.app_name)));
            txtInfoTittleLogin.setText(getString(R.string.strSignInDetail));
            txtPolicy.setText("");

            Link log_Link = new Link(getString(R.string.strHeaderSignUp))
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true);                                           // optional, defaults to false
            btnOtherLogin.setText(getString(R.string.strIsSignIn) + " " + getString(R.string.strHeaderSignUp));
            LinkBuilder.on(btnOtherLogin).addLink(log_Link).build();
        } else {
            txtTittleLogin.setText(getString(R.string.strHeaderSignUp) + " " + context.getString(R.string.app_name));
            txtInfoTittleLogin.setText(getString(R.string.strSignUpDetail));
            Link terms_Of_Service_Link = new Link(getString(R.string.strTermsOfService))
                    .setTextColor(Color.parseColor("#000000"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                              // optional, defaults to false
                    .setOnClickListener(clickedText -> {
                        // single clicked
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/terms?hl=vi&amp;gl=VN"));
                        context.startActivity(browserIntent);
                    });
            Link privacy_Policy_Link = new Link(getString(R.string.strPrivacyPolicy))
                    .setTextColor(Color.parseColor("#000000"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                              // optional, defaults to false
                    .setOnClickListener(clickedText -> {
                        // single clicked
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://policies.google.com/privacy?hl=vi&amp;gl=VN"));
                        context.startActivity(browserIntent);
                    });

            txtPolicy.setText(getString(R.string.strTermsAndPermissions));
            LinkBuilder.on(txtPolicy).addLink(terms_Of_Service_Link).build();
            LinkBuilder.on(txtPolicy).addLink(privacy_Policy_Link).build();

            txtPolicy.setMovementMethod(LinkMovementMethod.getInstance());

            Link log_Link = new Link(getString(R.string.strHeaderSignIn))
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true);                                            // optional, defaults to false
            btnOtherLogin.setText(getString(R.string.strIsSignUp) + " " + getString(R.string.strHeaderSignIn));
            LinkBuilder.on(btnOtherLogin).addLink(log_Link).build();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void addEvents() {
        imgBtnBack.setOnClickListener(view -> dismiss());
        accountTypeAdapter.setOnLoadMoreListener(() -> {
            accountTypess.add(null);
            accountTypeAdapter.notifyItemInserted(accountTypess.size() - 1);
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
        });
        btnOtherLogin.setOnClickListener(view -> {
            login = !login;
            init();
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
        accountTypes.add(new AccountType(1 + "", getString(R.string.strUseEmail), R.drawable.ic_user1));
        accountTypes.add(new AccountType(2 + "", getString(R.string.strContinueGoogle), R.drawable.ic_google));
        accountTypes.add(new AccountType(3 + "", getString(R.string.strContinueFacebook), R.drawable.ic_facebook));
        accountTypes.add(new AccountType(4 + "", getString(R.string.strContinueTwitter), R.drawable.ic_twitter));
        accountTypes.add(new AccountType(5 + "", getString(R.string.strContinueInstagram), R.drawable.ic_instagram));
        accountTypes.add(new AccountType(6 + "", getString(R.string.strContinueLine), R.drawable.ic_line));
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

//        private final int VIEW_TYPE_ITEM = 0;
//        private final int VIEW_TYPE_LOADING = 1;
        OnLoadMoreListener onLoadMoreListener;
        public boolean isLoading = false;
        int visibleThrehold = 3;
        int lastVisibleItem;
        int totalItem;

//        public OnLoadMoreListener getOnLoadMoreListener() {
//            return onLoadMoreListener;
//        }

        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
            this.onLoadMoreListener = onLoadMoreListener;
        }

        public AccountTypeAdapter() {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerViewAccountType.getLayoutManager();
            imgBtnLoadMore.setOnClickListener(view -> {
                ImageButton btn = (ImageButton) view;
                btn.setVisibility(View.GONE);
                assert linearLayoutManager != null;
                totalItem = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItem <= (lastVisibleItem + visibleThrehold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
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
            View accountTypeView = LayoutInflater.from(context).inflate(R.layout.item_account_type, parent, false);
            return new AccountTypeViewHolder(accountTypeView);
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
            holder.imgAccountType.setImageResource(accountType.getIdIcon());
            holder.txtAccountType.setText(accountType.getName());

            holder.setItemClickListener((view, position1, isLongClick) -> {
                if (!isLongClick) {
                    switch (accountType.getIdAccountType()) {
                        case "1":
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("login", login);
                            context.startActivity(intent);
                            break;
                        default:
                            Toast.makeText(context.getBaseContext(), getString(R.string.strNotifyComingSoon), Toast.LENGTH_SHORT).show();
                            break;
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

//        @Override
//        public int getItemViewType(int position) {
//            return accountTypess.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
//        }
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

//    static class LoadingViewHolder extends RecyclerView.ViewHolder {
//
//        public ProgressBar progressBar;
//
//        public LoadingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.progressBar);
//        }
//    }

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
