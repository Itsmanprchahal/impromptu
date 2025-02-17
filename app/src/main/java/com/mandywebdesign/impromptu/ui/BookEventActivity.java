package com.mandywebdesign.impromptu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mandywebdesign.impromptu.Adapters.B_EventDetailImageAdapter;
import com.mandywebdesign.impromptu.Adapters.Booked_users;
import com.mandywebdesign.impromptu.BusinessRegisterLogin.BusinessUserPRofileActivity;
import com.mandywebdesign.impromptu.BusinessRegisterLogin.BusinessUserProfile;
import com.mandywebdesign.impromptu.BusinessRegisterLogin.SeeAll_activity;
import com.mandywebdesign.impromptu.Home_Screen_Fragments.Home;
import com.mandywebdesign.impromptu.Retrofit.FollowUnfollow;
import com.mandywebdesign.impromptu.Retrofit.RefundAPI;
import com.mandywebdesign.impromptu.Retrofit.RefundRequest;
import com.mandywebdesign.impromptu.SettingFragmentsOptions.NormalGetProfile;
import com.mandywebdesign.impromptu.Utils.Constants;
import com.mandywebdesign.impromptu.messages.ChatBoxActivity;
import com.mandywebdesign.impromptu.Interfaces.WebAPI;
import com.mandywebdesign.impromptu.R;
import com.mandywebdesign.impromptu.Retrofit.BookFreeEvents;
import com.mandywebdesign.impromptu.Retrofit.Booked_User;
import com.mandywebdesign.impromptu.Retrofit.EventMessageClick;
import com.mandywebdesign.impromptu.Retrofit.NormalRetroFav;
import com.mandywebdesign.impromptu.Retrofit.NormalRetrodeleteFav;
import com.mandywebdesign.impromptu.Retrofit.RemainingTickets;
import com.mandywebdesign.impromptu.Retrofit.RetroGetEventData;
import com.mandywebdesign.impromptu.SettingFragmentsOptions.NormalPublishProfile;
import com.mandywebdesign.impromptu.Utils.Util;
import com.mandywebdesign.impromptu.stripeconnect.ConnectStripe;
import com.yarolegovich.mp.util.Utils;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private LinearLayout dotsLayout, ticketnumberlayout;
    ImageView sharevent, screenshot;
    private TextView[] dots;
    ImageView book_message, backonbookevent;
    LinearLayout organiser_layout;
    RelativeLayout messagelayout, invite_layouit;
    FragmentManager fragmentManager;
    Button mBookEvent, askforrefund;
    ImageButton follow_button;
    ImageView dashline2;
    TextView organiserName, ticketnumbers, eventdistance, book_time, book_time2, book_categry, peoplegoing, seeAll, remainingTicketTV, invitefriends, dialogtickttype, link1, link2, link3;
    ViewPager viewPager;
    RecyclerView users;
    ReadMoreTextView descri;
    RoundedImageView host_pic;
    PagerAdapter pagerAdapter;
    Dialog progressDialog;
    TextView ticketPrice, book_location, book_date, totalPrice, event_title, eventprice, tickettype, dailog_ticket_type;
    Spinner spinner, ticketype_spinner;
    Button dialogButoon;
    public static CheckBox addtoFavCheck_box;
    View view;
    String checkgender, booklink1, booklink2, booklink3;
    SharedPreferences.Editor editor, editorItemPos;
    SharedPreferences sharedPreferences, itemPositionPref;
    Bundle bundle;
    ArrayList<String> userImage = new ArrayList<>();
    ArrayList<String> user_id = new ArrayList<>();
    ArrayList<String> user_booked_tickets = new ArrayList<>();
    TextView cal1, cal3;
    LinearLayout cal2, cal4;
    static String tot;
    static String InviteFriend;
    static String total_ticket;
    static String loginUserId;
    static String hostUserID;
    String lat, lng;
    static String remaini_tickets, getTickets_booked_by_user, remaining_tickets;
    String refundTicketType, usertype, eventType, event_book, tickettypedialog;
    String itemPos, event_status;
    static String value, S_token, BToken, fav_id, hostname, payvalue, spinnerposition, tickettypespinnerposintion = "", tickettypeS;
    public static ArrayList<String> image = new ArrayList<>();
    public static String id, cate, host_image, transaction_id, tickets_booked_by_user, date, decs, follow_status, postcode, ticktType, ticktprice, timefrom, hostimage, timeto, title, location, location2, city, gender, andendeenumber, numberoftickts, freeEvent, username;
    int CurrentPage = 0;
    AlertDialog.Builder builder;
    Intent intent;
    ArrayList<String> tickettypes = new ArrayList<>();
    ArrayList<String> ticketprice = new ArrayList<>();
    ArrayList<RetroGetEventData.TicketsType> ticketPending = new ArrayList<>();
    static String tickettypeposition, getSpinnerposition = "1";
    static String currentlat, currenlng, eventlat, eventlng;
    ArrayAdapter<String> adapter;
    Dialog dialog, dialog1, refundDialog, refundBusinessDialog;
    TextView dailog_ticket_type1, dailog_ticket_price1, eventid;
    Spinner spinner1;
    Button dialogButoon1;
    EditText reasonrefund;
    String event_user_type;
    int refundticketCount, pendingTickets, count;
    RelativeLayout attendeelayout;
    Button oneTicket, TwoTicket, confirmrefund, connectOk, connectCancel;
    String ticketCount = "1";
    ArrayList<String> refundTickets = new ArrayList<>();
    ArrayList<String> refundBookedTickets = new ArrayList<>();
    ArrayList<String> refundBookedTicketsCount = new ArrayList<>();
    ArrayList<RetroGetEventData.TotalTicketsUser> bookedtictetdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_event);
        builder = new AlertDialog.Builder(BookEventActivity.this);
        builder.setMessage("Coming soon...");
        spinnerposition = "1";
        fragmentManager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences("UserToken", Context.MODE_PRIVATE);
        itemPositionPref = getSharedPreferences("ItemPosition", Context.MODE_PRIVATE);
        BToken = sharedPreferences.getString("Usertoken", "");
        editor = sharedPreferences.edit();
        editorItemPos = itemPositionPref.edit();
        S_token = sharedPreferences.getString("Socailtoken", "");
        loginUserId = sharedPreferences.getString("userID", "");
        itemPos = itemPositionPref.getString("itemPosition", "");

        progressDialog = ProgressBarClass.showProgressDialog(this);
        progressDialog.dismiss();
        Drawable drawable = new ProgressBar(BookEventActivity.this).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(BookEventActivity.this, R.color.colorTheme),
                PorterDuff.Mode.SRC_IN);

        getProfile("Bearer " + S_token);

        init();
        listeners();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(BookEventActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        users.setLayoutManager(layoutManager);
        users.setNestedScrollingEnabled(false);


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkgender = sharedPreferences.getString("profilegender", "");
        intent = getIntent();
        if (intent != null) {
            currentlat = intent.getStringExtra("lat");
            currenlng = intent.getStringExtra("lng");
            value = intent.getStringExtra("event_id");
            payvalue = intent.getStringExtra("back_pay");
            eventType = intent.getStringExtra("eventType");
            //  fav_id = bundle.getString("fav_id");
            hostname = intent.getStringExtra("hostname");
            hostimage = intent.getStringExtra("hostImage");
            if (hostname != null) {
                String[] name = hostname.split(" ");
                if (name.length == 1) {
                    String Fname = name[0];
                    organiserName.setText(Fname + " ");
                } else {
                    String Fname = name[0];
                    String Lname = name[1];
                    organiserName.setText(Fname + " " + Lname.subSequence(0, 1));
                }
            } else {
                organiserName.setText(hostname);
            }
            book_message.setVisibility(View.VISIBLE);


            if (eventType != null) {

                if (eventType.equals("live")) {
                    mBookEvent.setVisibility(View.VISIBLE);
                    invite_layouit.setVisibility(View.VISIBLE);
                } else if (eventType.equals("upcoming")) {
                    mBookEvent.setVisibility(View.GONE);
                    invite_layouit.setVisibility(View.GONE);
//                    askforrefund.setVisibility(View.VISIBLE);
                    mBookEvent.setVisibility(View.VISIBLE);
                } else {
                    mBookEvent.setVisibility(View.GONE);
                    invite_layouit.setVisibility(View.GONE);
//                    askforrefund.setVisibility(View.GONE);
                    peoplegoing.setVisibility(View.GONE);
                }
                if (eventType.equals("fav")) {
                    eventdistance.setVisibility(View.GONE);
                    invite_layouit.setVisibility(View.VISIBLE);
                }
            } else {
                mBookEvent.setVisibility(View.VISIBLE);
                invite_layouit.setVisibility(View.VISIBLE);
            }
        }

        if (!S_token.equalsIgnoreCase("")) {
            try {
                FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(getIntent())
                        .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                            @Override
                            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                // Get deep link from result (may be null if no link is found)
                                Uri deepLink = null;
                                if (pendingDynamicLinkData != null) {
                                    deepLink = pendingDynamicLinkData.getLink();
                                    String path = deepLink.getPath();
                                    Log.d("deeplink1", path);
                                    String[] evetdata = path.split("/");
                                    String eventID;
                                    if (evetdata.length == 1) {
                                        eventID = evetdata[1];
                                    } else {
                                        eventID = evetdata[2];
                                    }

                                    Log.d("deeplink", eventID);
                                    getEventData(S_token, eventID);
                                    addFav(S_token, eventID);
                                    getRaminingEvents(S_token, eventID);
                                    getUsers(S_token, eventID);
                                    bookevent(eventID);
                                    gotoEventMesg(S_token, eventID);
                                    askforrefund("Bearer " + S_token, eventID);
                                } else {
                                    getEventData(S_token, value);
                                    addFav(S_token, value);
                                    getRaminingEvents(S_token, value);
                                    getUsers(S_token, value);
                                    bookevent(value);
                                    gotoEventMesg(S_token, value);
                                    askforrefund("Bearer " + S_token, value);
                                }

                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("dynamiclink", "getDynamicLink:onFailure", e);
                            }
                        });
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store")));
            }

        } else if (!BToken.equalsIgnoreCase("")) {

            try {
                FirebaseDynamicLinks.getInstance()
                        .getDynamicLink(getIntent())
                        .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                            @Override
                            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                // Get deep link from result (may be null if no link is found)
                                Uri deepLink = null;
                                if (pendingDynamicLinkData != null) {
                                    deepLink = pendingDynamicLinkData.getLink();
                                    String path = deepLink.getPath();
                                    Log.d("deeplink1", path);
                                    String[] evetdata = path.split("/");
                                    String eventID;
                                    if (evetdata.length == 1) {
                                        eventID = evetdata[1];
                                    } else {
                                        eventID = evetdata[2];
                                    }

                                    Log.d("deeplink", eventID);
                                    getEventData(BToken, eventID);
                                    addtoFavCheck_box.setVisibility(View.GONE);
                                    mBookEvent.setVisibility(View.GONE);
                                    follow_button.setVisibility(View.GONE);
                                    getRaminingEvents(BToken, eventID);
                                    getUsers(BToken, eventID);

                                } else {

                                   /* getEventData(S_token, value);
                                    addFav(S_token, value);
                                    getRaminingEvents(S_token, value);
                                    getUsers(S_token, value);
                                    bookevent(value);
                                    gotoEventMesg(S_token, value);
                                    askforrefund("Bearer " + S_token, value);*/
                                }

                            }
                        })
                        .addOnFailureListener(this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("dynamiclink", "getDynamicLink:onFailure", e);
                            }
                        });
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store")));
            }
        } else {
            Intent intent = new Intent(this, Join_us.class);
            startActivity(intent);
            finish();
        }

    }

    private void askforrefund(final String s_token, final String value) {


        askforrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(event_user_type.equals("normal"))
//                {
//               RefundDialog();
//                }else {

                RefundDialog(event_user_type);
//                }

//                ConfirmationDialog(s_token, value);
            }
        });
    }

    private void RefundDialog(String event_user_type) {
        refundBusinessDialog = new Dialog(BookEventActivity.this);
        refundBusinessDialog.setContentView(R.layout.custom_dialog_book_ticket);
        refundBusinessDialog.setCanceledOnTouchOutside(true);
        refundBusinessDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        refundBusinessDialog.show();
        FindId1(refundBusinessDialog);

        if (event_user_type.equals("normal")) {
            ticketype_spinner.setVisibility(View.GONE);
            tickettype.setText("Ticket Type");
            dailog_ticket_type.setText(ticktType);
            refundTicketType = dailog_ticket_type.getText().toString();

        } else {
            dailog_ticket_type.setVisibility(View.GONE);
            ticketype_spinner.setVisibility(View.VISIBLE);
            dialogtickttype.setVisibility(View.GONE);
            if (refundTickets.size() >= 2) {
                tickettype.setText("Ticket Types");
            }
        }


        //Todo: ticket type spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BookEventActivity.this, android.R.layout.simple_spinner_item, refundTickets);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketype_spinner.setAdapter(arrayAdapter);
        Log.d("ticketCounts", "" + refundBookedTickets);
        refundticketCount = Integer.parseInt(refundBookedTickets.get(0));

        ticketype_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ToDo: Ticket Type Spinner:
                tickettypespinnerposintion = parent.getItemAtPosition(position).toString();
                tickettypeposition = String.valueOf(position);
                refundTicketType = tickettypespinnerposintion;
                refundBookedTicketsCount.clear();
                for (int i = 0; i < Integer.valueOf(refundBookedTickets.get(Integer.parseInt(tickettypeposition))); i++) {
                    refundBookedTicketsCount.add(String.valueOf(i + 1));
                }
                spinner.setSelection(0);
                ticktprice = ticketprice.get(Integer.parseInt(tickettypeposition));
//                ticketnumbers.setText(refundBookedTickets.get(Integer.parseInt(tickettypeposition)));

                Float a = Float.valueOf((getSpinnerposition));
                total_ticket = String.valueOf(a);

                String tickt = ticktprice;
                Float b = Float.valueOf((tickt));

                Float total = a * b;

                tot = String.valueOf(total);

                ticketPrice.setText(ticktprice);
                totalPrice.setText(tot);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //============================88888888888888888888888888=====================================
        refundBookedTicketsCount.clear();

        for (int i = 0; i < refundticketCount; i++) {
            refundBookedTicketsCount.add(String.valueOf(i + 1));
        }

        adapter = new ArrayAdapter<String>(BookEventActivity.this,
                android.R.layout.simple_spinner_item, refundBookedTicketsCount);

        ticketPrice.setText(ticktprice);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int ticketCountposition = position + 1;


                Float ticktprice1 = Float.valueOf(ticketPrice.getText().toString());
                ticktprice = String.valueOf(ticktprice1);

                Float a = Float.valueOf((ticketCountposition));
                //ToDo: Numbers of tickets:
                total_ticket = String.valueOf(ticketCountposition);

                String tickt = String.valueOf(ticktprice);
                Float b = Float.valueOf((tickt));

                Float total = ticketCountposition * ticktprice1;

                tot = String.valueOf(total);

                ticketPrice.setText(String.valueOf(ticktprice1));
                totalPrice.setText(tot);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialogButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("refundtest", refundTicketType + "  " + total_ticket + "  " + reasonrefund.getText().toString() + "   " + totalPrice.getText().toString());

                if (!reasonrefund.getText().toString().equals("")) {
                    progressDialog.show();
                    Call<RefundRequest> refundRequestCall = WebAPI.getInstance().getApi().refundRequest("Bearer " + S_token, value, reasonrefund.getText().toString(), refundTicketType, totalPrice.getText().toString(), total_ticket);
                    refundRequestCall.enqueue(new Callback<RefundRequest>() {
                        @Override
                        public void onResponse(Call<RefundRequest> call, Response<RefundRequest> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                refundBusinessDialog.dismiss();
                                Toast.makeText(BookEventActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent(BookEventActivity.this, NoInternetScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<RefundRequest> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    reasonrefund.setError("Enter reason for refund");
                }

            }
        });
    }

    private void FindId1(Dialog refundBusinessDialog) {
        reasonrefund = refundBusinessDialog.findViewById(R.id.reasonrefund);
        reasonrefund.setVisibility(View.VISIBLE);
        dailog_ticket_type = refundBusinessDialog.findViewById(R.id.dailog_ticket_type);
        tickettype = refundBusinessDialog.findViewById(R.id.tickettype);
        ticketPrice = refundBusinessDialog.findViewById(R.id.dailog_ticket_price);
        totalPrice = refundBusinessDialog.findViewById(R.id.dailog_total_price);
        spinner = refundBusinessDialog.findViewById(R.id.dailog_spinner);
        ticketype_spinner = refundBusinessDialog.findViewById(R.id.ticketype_spinner);
        dialogButoon = refundBusinessDialog.findViewById(R.id.dailog_button);
        dialogtickttype = refundBusinessDialog.findViewById(R.id.dailog_ticket_type);
        cal1 = refundBusinessDialog.findViewById(R.id.cal1);
        cal2 = refundBusinessDialog.findViewById(R.id.cal2);
        cal3 = refundBusinessDialog.findViewById(R.id.cal3);
        cal4 = refundBusinessDialog.findViewById(R.id.cal4);
    }


    private void RefundDialog() {
        refundDialog = new Dialog(this);
        Window window = refundDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        refundDialog.setContentView(R.layout.refundticketdialog);
        refundDialog.setCancelable(true);
        refundDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        refundDialog.show();

        oneTicket = refundDialog.findViewById(R.id.oneticket);
        TwoTicket = refundDialog.findViewById(R.id.twoticket);
        confirmrefund = refundDialog.findViewById(R.id.confirmrefund);
        EditText reason_for_refund_ET = refundDialog.findViewById(R.id.reason_for_refund_et);

        oneTicket.setBackground(getResources().getDrawable(R.drawable.button_color_theme));
        oneTicket.setTextColor(getResources().getColor(R.color.colortextwhite));

        TwoTicket.setBackground(getResources().getDrawable(R.drawable.linearback));
        TwoTicket.setTextColor(getResources().getColor(R.color.colorTheme));

        if (tickets_booked_by_user.equals("1")) {
            TwoTicket.setVisibility(View.GONE);
        } else {
            TwoTicket.setVisibility(View.VISIBLE);
        }

        oneTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneTicket.setBackground(getResources().getDrawable(R.drawable.button_color_theme));
                oneTicket.setTextColor(getResources().getColor(R.color.colortextwhite));

                TwoTicket.setBackground(getResources().getDrawable(R.drawable.linearback));
                TwoTicket.setTextColor(getResources().getColor(R.color.colorTheme));
                ticketCount = "1";
            }
        });

        TwoTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwoTicket.setBackground(getResources().getDrawable(R.drawable.button_color_theme));
                TwoTicket.setTextColor(getResources().getColor(R.color.colortextwhite));

                oneTicket.setBackground(getResources().getDrawable(R.drawable.linearback));
                oneTicket.setTextColor(getResources().getColor(R.color.colorTheme));
                ticketCount = "2";
            }
        });

        confirmrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reason_for_refund_ET.getText().toString().equals("")) {
                    reason_for_refund_ET.setError("Enter reasor for refund");
                    reason_for_refund_ET.setFocusable(true);
                } else {
                    refundDialog.dismiss();
                }

            }
        });

    }

    private void gotoEventMesg(final String token, final String value) {
        book_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkgender.equals("")) {
                    updateProfiledialog();

                } else {
                    if (event_book.equals("0")) {
                        Toast.makeText(BookEventActivity.this, "Book event to send message.", Toast.LENGTH_SHORT).show();
                    } else {
                        final Intent intent = new Intent(BookEventActivity.this, ChatBoxActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("event_title", title);
                        intent.putExtra("event_image", image.get(0));
                        intent.putExtra("eventID", value);
                        intent.putExtra("event_host_user", hostUserID);


                        Call<EventMessageClick> call = WebAPI.getInstance().getApi().eventMEsgClick("Bearer " + token, id);
                        call.enqueue(new Callback<EventMessageClick>() {
                            @Override
                            public void onResponse(Call<EventMessageClick> call, Response<EventMessageClick> response) {
                                if (response.body() != null) {
                                    if (response.body().getStatus().equals("200")) {
                                        startActivity(intent);
                                    }

                                } else {
                                    Intent intent = new Intent(BookEventActivity.this, NoInternetScreen.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }

                            }

                            @Override
                            public void onFailure(Call<EventMessageClick> call, Throwable t) {
                                Toast.makeText(BookEventActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }

    public void ConfirmationDialog(final String s_token, final String usrToken) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.refunddialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes = dialog.findViewById(R.id.yesdialog);
        Button no = dialog.findViewById(R.id.nodialog);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Call<RefundAPI> refundAPICall = WebAPI.getInstance().getApi().refundapi(s_token, value);
                refundAPICall.enqueue(new Callback<RefundAPI>() {
                    @Override
                    public void onResponse(Call<RefundAPI> call, Response<RefundAPI> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals("200")) {
                                Toast.makeText(BookEventActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BookEventActivity.this, Home_Screen.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(BookEventActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(BookEventActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RefundAPI> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(BookEventActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void bookevent(final String value) {
        mBookEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkgender.equals("")) {
                    //dialogUpdate();
                    updateProfiledialog();
                } else {

                    if (!tickettypedialog.equals("Free")) {
                        dialog(value);
                    } else {
                        dialog1(value);

                    }

                }
            }
        });

    }

    public void dialog(final String value) {
        ArrayList<Integer> ticketNums = new ArrayList<>();
        String[] ar = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        String[] ticketNum = new String[]{"1", "2"};
        String[] ticketNum1 = new String[]{"1"};
        ArrayList<String> ticketOne = new ArrayList<>();

        dialog = new Dialog(BookEventActivity.this);
        dialog.setContentView(R.layout.custom_dialog_book_ticket);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        FindId(dialog);

        dailog_ticket_type.setVisibility(View.GONE);
        ticketype_spinner.setVisibility(View.VISIBLE);
        dialogtickttype.setVisibility(View.GONE);
        if (tickettypes.size() >= 2) {
            tickettype.setText("Ticket Types");
        }

        if (tickettypes.size() == 0) {
            if (tickets_booked_by_user.equals("0")) {

                if (remaining_tickets.equals("1")) {
                    adapter = new ArrayAdapter<String>(BookEventActivity.this,
                            android.R.layout.simple_spinner_item, ticketNum1);
                } else {
                    adapter = new ArrayAdapter<String>(BookEventActivity.this,
                            android.R.layout.simple_spinner_item, ticketNum);

                }
            } else {
                adapter = new ArrayAdapter<String>(BookEventActivity.this,
                        android.R.layout.simple_spinner_item, ticketNum1);
            }

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
//        spinner.setSelection(0);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String ticketCount = parent.getItemAtPosition(position).toString();
                    int ticketCountposition = position + 1;

                    Float ticktprice1 = Float.valueOf(ticketPrice.getText().toString());
                    ticktprice = String.valueOf(ticktprice1);

                    Float a = Float.valueOf((ticketCountposition));
                    total_ticket = String.valueOf(ticketCountposition);

                    String tickt = String.valueOf(ticktprice);
                    Float b = Float.valueOf((tickt));

                    Float total = ticketCountposition * ticktprice1;

                    tot = String.valueOf(total);

                    ticketPrice.setText(String.valueOf(ticktprice1));
                    totalPrice.setText(tot);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            //Todo: ticket type spinner
            Log.d("ticketTypes", "" + tickettypes);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BookEventActivity.this, android.R.layout.simple_spinner_item, tickettypes);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ticketype_spinner.setAdapter(arrayAdapter);
//        ticketype_spinner.setSelection(0);
            ticketype_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    tickettypespinnerposintion = parent.getItemAtPosition(position).toString();
                    tickettypeposition = String.valueOf(position);


                    ticktprice = ticketprice.get(Integer.parseInt(tickettypeposition));

                    pendingTickets = ticketPending.get(Integer.parseInt(tickettypeposition)).getPending_tickets();
                    ticketOne.clear();
                    if (pendingTickets < 2) {
                        for (int i = 0; i < 1; i++) {
                            ticketOne.add(String.valueOf(i + 1));
                        }
                    } else {
                        for (int i = 0; i < 2; i++) {
                            ticketOne.add(String.valueOf(i + 1));
                        }
                    }

                    spinner.setSelection(0);

                    Float a = Float.valueOf((getSpinnerposition));
                    total_ticket = String.valueOf(a);

                    String tickt = ticktprice;
                    Float b = Float.valueOf((tickt));

                    Float total = a * b;

                    tot = String.valueOf(total);

                    ticketPrice.setText(ticktprice);
                    totalPrice.setText(tot);

                    if (ticketOne.size() == 1) {
                        adapter = new ArrayAdapter<String>(BookEventActivity.this,
                                android.R.layout.simple_spinner_item, ticketNum1);
                    } else if (ticketOne.size() == 2) {

                        if (tickets_booked_by_user.equals("0")) {

                            adapter = new ArrayAdapter<String>(BookEventActivity.this,
                                    android.R.layout.simple_spinner_item, ticketNum);
                        } else {
                            adapter = new ArrayAdapter<String>(BookEventActivity.this,
                                    android.R.layout.simple_spinner_item, ticketNum1);
                        }
                    }

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
//        spinner.setSelection(0);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String ticketCount = parent.getItemAtPosition(position).toString();
                            int ticketCountposition = position + 1;

                            Float ticktprice1 = Float.valueOf(ticketPrice.getText().toString());
                            ticktprice = String.valueOf(ticktprice1);

                            Float a = Float.valueOf((ticketCountposition));
                            total_ticket = String.valueOf(ticketCountposition);

                            String tickt = String.valueOf(ticktprice);
                            Float b = Float.valueOf((tickt));

                            Float total = ticketCountposition * ticktprice1;

                            tot = String.valueOf(total);

                            ticketPrice.setText(String.valueOf(ticktprice1));
                            totalPrice.setText(tot);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


//        if (!event_user_type.equals("normal"))
//        {
//            adapter = new ArrayAdapter<String>(BookEventActivity.this,
//                    android.R.layout.simple_spinner_item, ar);
//        }else {
//        }
        ticketPrice.setText(ticktprice);
        //===================================ppppppppppppppppppppppppppppppppppp==============================
        if (spinnerposition != null) {
            spinner.setSelection(Integer.parseInt(spinnerposition) - 1);
        }


        if (ticktType != null) {
            if (ticktType.equals("")) {
                dailog_ticket_type.setText("Free");

            } else {
                dailog_ticket_type.setText(ticktType);
                ticketype_spinner.setVisibility(View.GONE);
                dialogtickttype.setVisibility(View.VISIBLE);
            }
        } else {
            dialogtickttype.setVisibility(View.VISIBLE);
        }

        dialogButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float TotalTIcket = Float.valueOf(((total_ticket)));

                Float RemainingTIckets = Float.valueOf((remaini_tickets));
                String ticket1 = tickets_booked_by_user + ".0";
                String total_ticket = String.valueOf(TotalTIcket);

                if (TotalTIcket > RemainingTIckets) {
                    adapter = new ArrayAdapter<String>(BookEventActivity.this,
                            android.R.layout.simple_spinner_item, ticketNum);
                } else {
                    Intent intent = new Intent(BookEventActivity.this, PayActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("total_price", tot);
                    intent.putExtra("event_id", value);
                    intent.putExtra("event_Title", title);
                    intent.putExtra("total_tickets", total_ticket);
                    intent.putExtra("ticket_Price", ticktprice);
                    intent.putExtra("imagesend", "BEA");
                    intent.putExtra("tickettype", tickettypespinnerposintion);
                    startActivity(intent);
                    tickettypes.clear();
                    dialog.dismiss();

                }

            }
        });
    }

    private void dialog1(String value) {
        String[] ar = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] ticketNum = new String[]{"1", "2"};
        String[] ticketNum1 = new String[]{"1"};

        dialog1 = new Dialog(BookEventActivity.this);
        dialog1.setContentView(R.layout.freeeventdialog);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.show();
        FindIdFree(dialog1);


        if (tickets_booked_by_user.equals("0") && !remaini_tickets.equals("1")) {
            adapter = new ArrayAdapter<String>(BookEventActivity.this,
                    android.R.layout.simple_spinner_item, ticketNum);
        } else {
            adapter = new ArrayAdapter<String>(BookEventActivity.this,
                    android.R.layout.simple_spinner_item, ticketNum1);
        }


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        if (spinnerposition != null) {
            spinner1.setSelection(Integer.parseInt(spinnerposition) - 1);
        }


        if (ticktType != null) {
            if (ticktType.equals("")) {
                dailog_ticket_type1.setText("Free");
            } else {
                dailog_ticket_type1.setText(ticktType);
            }
        } else {

        }
        dialogButoon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float TotalTIcket = Float.valueOf(((total_ticket)));

                Float RemainingTIckets = Float.valueOf((remaini_tickets));
                String ticket = tickets_booked_by_user + ".0";
                String total_ticket = String.valueOf(TotalTIcket);

                if (TotalTIcket >= RemainingTIckets) {
                    adapter = new ArrayAdapter<String>(BookEventActivity.this,
                            android.R.layout.simple_spinner_item, ticketNum1);
                    freebookevent(dialog1);
                } else {

                    if (ticket.equals(total_ticket)) {
                        freebookevent(dialog1);
                    } else if (ticket.equals("0.0")) {
                        freebookevent(dialog1);
                    } else {
                        Toast.makeText(BookEventActivity.this, "You can buy one more ticket only", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        dialog1.show();

    }

    private void getProfile(String socailtoken) {
        progressDialog.show();
        Call<com.mandywebdesign.impromptu.Retrofit.NormalGetProfile> call = WebAPI.getInstance().getApi().normalGetPRofile(socailtoken, "application/json", "");
        call.enqueue(new Callback<com.mandywebdesign.impromptu.Retrofit.NormalGetProfile>() {
            @Override
            public void onResponse(Call<com.mandywebdesign.impromptu.Retrofit.NormalGetProfile> call, Response<com.mandywebdesign.impromptu.Retrofit.NormalGetProfile> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    editor = sharedPreferences.edit();
                    editor.putString("profilegender", response.body().getData().get(0).getGender());
                    editor.apply();

                }
            }

            @Override
            public void onFailure(Call<com.mandywebdesign.impromptu.Retrofit.NormalGetProfile> call, Throwable t) {

            }
        });
    }

    public void updateProfiledialog() {
        final Dialog dialog = new Dialog(BookEventActivity.this);
        dialog.setContentView(R.layout.welcomedialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        Button continue_bt = dialog.findViewById(R.id.continue_bt);
        continue_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(BookEventActivity.this, NormalPublishProfile.class);
                intent.putExtra("normal_edit", "0");
                intent.putExtra("backongendercheck", "0");
                startActivity(intent);
            }
        });
    }

    //check remainig tickets
    private void getRaminingEvents(String s_token, String id) {

        Call<RemainingTickets> call = WebAPI.getInstance().getApi().remainingTickets("Bearer " + s_token, id);
        call.enqueue(new Callback<RemainingTickets>() {
            @Override
            public void onResponse(Call<RemainingTickets> call, Response<RemainingTickets> response) {

                if (response.body() != null) {
                    remaini_tickets = String.valueOf(response.body().getData().getRemaining());
                    remainingTicketTV.setText("Tickets Remaining " + remaini_tickets);
                    if (remaini_tickets.equals("0")) {
                        mBookEvent.setVisibility(View.GONE);
                    }
                } else {
                    Intent intent = new Intent(BookEventActivity.this, NoInternetScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<RemainingTickets> call, Throwable t) {
                Toast.makeText(BookEventActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        attendeelayout = findViewById(R.id.attendeelayout);
        dashline2 = findViewById(R.id.dashline2);
        eventid = findViewById(R.id.eventid);
        book_time2 = findViewById(R.id.book_time2);
        eventdistance = findViewById(R.id.eventdistance);
        follow_button = findViewById(R.id.follow_button);
        screenshot = findViewById(R.id.screenshot);
        backonbookevent = findViewById(R.id.backonbookevent);
        book_time = findViewById(R.id.book_time);
        book_categry = findViewById(R.id.book_categry);
        eventprice = findViewById(R.id.event_price);
        dotsLayout = findViewById(R.id.book_indicator);
        viewPager = findViewById(R.id.book_viewpager);
        addtoFavCheck_box = findViewById(R.id.book_event_favourite);
        mBookEvent = findViewById(R.id.book_event_book_bt);
        descri = findViewById(R.id.book_description);
        book_location = findViewById(R.id.book_location);
        book_date = findViewById(R.id.book_date);
        event_title = findViewById(R.id.book_tittle);
        host_pic = findViewById(R.id.book_user_picture);
        organiserName = findViewById(R.id.book_organiser_name);
        peoplegoing = findViewById(R.id.book_attendess_people);
        users = findViewById(R.id.book_recyclerView);
        seeAll = findViewById(R.id.book_seeAll);
        book_message = findViewById(R.id.book_message);
        organiser_layout = findViewById(R.id.organiser_layout);
        messagelayout = findViewById(R.id.messagelayout);
        invite_layouit = findViewById(R.id.invite_layouit);
        remainingTicketTV = findViewById(R.id.remainingTicketTV);
        invitefriends = findViewById(R.id.invitefriends);
        link1 = findViewById(R.id.book_link1);
        link2 = findViewById(R.id.book_link2);
        link3 = findViewById(R.id.book_link3);
        sharevent = findViewById(R.id.sharevent);
        askforrefund = findViewById(R.id.askforrefund);

    }

    private void listeners() {

       /* if (!getTickets_booked_by_user.equals("0"))
        {
            book_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lng + "&travelmode=driving";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });
        }*/


        sharevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  String message = "https://play.google.com/store";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(share, "Share Impromptu"));*/
                DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("https://www.impromptusocial.com/" + "event_id/" + value))
                        .setDomainUriPrefix("impromptusocial.page.link")
                        // Open links with this app on Android  amitpandey12.page.link
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build()).setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setDescription("Hi! Check out this Impromptu event.").build())
                        // Open links with com.example.ios on iOS
                        .setIosParameters(new DynamicLink.IosParameters.Builder("impromptusocial.page.link").build()).setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setDescription("Hi! Check out this Impromptu event.").build())
                        .buildDynamicLink();

                Uri dynamicLinkUri = dynamicLink.getUri();

                Log.d("hello123", "1" + dynamicLink.getUri());


                Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink().setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setDescription("Hey! Would you like to join me at this Impromptu event?").build())
                        .setLongLink(Uri.parse("https://" + dynamicLink.getUri().toString()))
                        .buildShortDynamicLink()
                        .addOnCompleteListener(BookEventActivity.this, new OnCompleteListener<ShortDynamicLink>() {
                            @Override
                            public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                if (task.isSuccessful()) {
                                    // Short link created
                                    Uri shortLink = task.getResult().getShortLink();
                                    Uri flowchartLink = task.getResult().getPreviewLink();
                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("text/plain");
                                    share.putExtra(Intent.EXTRA_TEXT, "Hi! Check out this Impromptu event." + "\n" + "\n" + shortLink.toString());
                                    startActivity(Intent.createChooser(share, "Share Event"));
                                } else {

                                }
                            }
                        });
            }
        });

        backonbookevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        invite_layouit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("https://www.impromptusocial.com/" + "event_id/" + value))
                        .setDomainUriPrefix("impromptusocial.page.link")
                        // Open links with this app on Android  amitpandey12.page.link
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build()).setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setDescription("Hey! Would you like to join me at this Impromptu event?").build())
                        // Open links with com.example.ios on iOS
                        .setIosParameters(new DynamicLink.IosParameters.Builder("impromptusocial.page.link").build())
                        .buildDynamicLink();

                Uri dynamicLinkUri = dynamicLink.getUri();

                Log.d("hello123", "1" + dynamicLink.getUri());


                Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLongLink(Uri.parse("https://" + dynamicLink.getUri().toString()))
                        .buildShortDynamicLink()
                        .addOnCompleteListener(BookEventActivity.this, new OnCompleteListener<ShortDynamicLink>() {
                            @Override
                            public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                if (task.isSuccessful()) {
                                    // Short link created
                                    Uri shortLink = task.getResult().getShortLink();
                                    Uri flowchartLink = task.getResult().getPreviewLink();
                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("text/plain");
                                    share.putExtra(Intent.EXTRA_TEXT, "Hey! Would you like to join me at this Impromptu event?" + "\n" + "\n" + shortLink.toString());
                                    startActivity(Intent.createChooser(share, "Event Invite"));
                                } else {

                                }
                            }
                        });

            }
        });

        builder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookEventActivity.this, SeeAll_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("isFrom", "BookEvent");
                intent.putExtra("value", id);
                startActivity(intent);
            }
        });


        link1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(booklink1);
                if (uri.getPath().contains("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Uri uri1 = Uri.parse("https://" + uri);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(intent);
                }
            }
        });

        link2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(booklink2);

                if (uri.getPath().contains("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Uri uri1 = Uri.parse("https://" + uri);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(intent);
                }
            }
        });

        link3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(booklink3);
                if (uri.getPath().contains("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Uri uri1 = Uri.parse("https://" + uri);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri1);
                    startActivity(intent);
                }
            }
        });

        follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<FollowUnfollow> call = WebAPI.getInstance().getApi().followunfollow("Bearer " + S_token, hostUserID);
                call.enqueue(new Callback<FollowUnfollow>() {
                    @Override
                    public void onResponse(Call<FollowUnfollow> call, Response<FollowUnfollow> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equals("Follow successfully.")) {
                                follow_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_filled));
                            } else {
                                follow_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_outline));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FollowUnfollow> call, Throwable t) {

                    }
                });
            }
        });

        host_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usertype.equals("business")) {
                    Intent intent = new Intent(BookEventActivity.this, BusinessUserPRofileActivity.class);
                    intent.putExtra("user_id", hostUserID);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(BookEventActivity.this, NormalGetProfile.class);
                    intent.putExtra("user_id", hostUserID);
                    startActivity(intent);
                }

            }
        });
    }

    private void FindIdFree(Dialog dialog) {

        dailog_ticket_type1 = dialog.findViewById(R.id.dailog_ticket_type1);
        spinner1 = dialog.findViewById(R.id.dailog_spinner1);
        dialogButoon1 = dialog.findViewById(R.id.dailog_button1);
        dailog_ticket_price1 = dialog.findViewById(R.id.dailog_ticket_price1);
    }

    private void freebookevent(final Dialog dialog) {
        progressDialog.show();
        Call<BookFreeEvents> call = WebAPI.getInstance().getApi().freebookevent("Bearer " + S_token, value, total_ticket);
        call.enqueue(new Callback<BookFreeEvents>() {
            @Override
            public void onResponse(Call<BookFreeEvents> call, Response<BookFreeEvents> response) {

                if (response.body() != null) {
                    progressDialog.dismiss();
                    dialog.dismiss();

                    Intent intent = new Intent(BookEventActivity.this, ConfirmationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("eventID", value);
                    intent.putExtra("paid", value);
                    editor.putString("eventImage", BookEventActivity.image.get(0));
                    editor.apply();
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(BookEventActivity.this, NoInternetScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<BookFreeEvents> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(BookEventActivity.this, "Book Error=> " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void FindId(Dialog dialog) {
        dailog_ticket_type = dialog.findViewById(R.id.dailog_ticket_type);
        tickettype = dialog.findViewById(R.id.tickettype);
        ticketPrice = dialog.findViewById(R.id.dailog_ticket_price);
        totalPrice = dialog.findViewById(R.id.dailog_total_price);
        spinner = dialog.findViewById(R.id.dailog_spinner);
        ticketype_spinner = dialog.findViewById(R.id.ticketype_spinner);
        dialogButoon = dialog.findViewById(R.id.dailog_button);
        dialogtickttype = dialog.findViewById(R.id.dailog_ticket_type);
        cal1 = dialog.findViewById(R.id.cal1);
        cal2 = dialog.findViewById(R.id.cal2);
        cal3 = dialog.findViewById(R.id.cal3);
        cal4 = dialog.findViewById(R.id.cal4);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        spinnerposition = parent.getItemAtPosition(position).toString();

        getSpinnerposition = spinnerposition;


        Float a = Float.valueOf((spinnerposition));
        total_ticket = String.valueOf(a);

        if (!tickettypedialog.equals("Free")) {
            String tickt = ticketPrice.getText().toString();
            Float b = Float.valueOf((tickt));

            Float total = a * b;

            tot = String.valueOf(total);

            totalPrice.setText(tot);
        } else {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void getUsers(String s_token, String value) {

        Call<Booked_User> call = WebAPI.getInstance().getApi().booked_users("Bearer " + s_token, value);
        call.enqueue(new Callback<Booked_User>() {
            @Override
            public void onResponse(Call<Booked_User> call, Response<Booked_User> response) {

                if (response.body() != null) {

                    Booked_User booked_users = response.body();
                    List<Booked_User.Datum> bookedUsersList = booked_users.getData();
                    userImage.clear();
                    user_id.clear();
                    user_booked_tickets.clear();

                    if (response.body().getStatus().equals("200")) {
                        for (int i = 0; i < bookedUsersList.size(); i++) {
                            userImage.add(response.body().getData().get(i).getFile());
                            user_id.add(response.body().getData().get(i).getUserid().toString());
                            user_booked_tickets.add(response.body().getData().get(i).getTotalTickets().toString());
                        }
                    } else {
                        peoplegoing.setText("no one has booked yet");
                        seeAll.setClickable(false);
                        seeAll.setVisibility(View.GONE);
                        users.setVisibility(View.GONE);
                    }

                    Booked_users adapter = new Booked_users(BookEventActivity.this, userImage, user_id, user_booked_tickets);
                    users.setAdapter(adapter);
                } else {
                    Intent intent = new Intent(BookEventActivity.this, NoInternetScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Booked_User> call, Throwable t) {
                Toast.makeText(BookEventActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getEventData(String token, final String value) {
        progressDialog.show();

        Call<RetroGetEventData> call = WebAPI.getInstance().getApi().getEvents("Bearer " + token, "application/json", value);
        call.enqueue(new Callback<RetroGetEventData>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<RetroGetEventData> call, Response<RetroGetEventData> response) {

                if (response.body() != null) {
                    if (response.body().getStatus().equals("200")) {
                        RetroGetEventData getEventData = response.body();
                        Log.d("123456", "" + getEventData.getData().size());
                        List<RetroGetEventData.Datum> datumList = getEventData.getData();
                        Log.d("events", "" + response.code() + " " + datumList.size());
                        for (RetroGetEventData.Datum datum : datumList) {
                            id = datum.getEventId().toString();
                            cate = datum.getCategory();
                            decs = datum.getDescription();
                            location = datum.getAddressline1();
                            location2 = datum.getAddressline2();
                            eventlat = datum.getLattitude();
                            eventlng = datum.getLongitude();
                            freeEvent = datum.getFreeEvent();
                            lat = datum.getLattitude();
                            event_user_type = datum.getEvent_host_type();
                            lng = datum.getLongitude();
                            eventid.setText("EID " + datum.getEventId().toString());
                            event_status = datum.getEvent_status();
                            remaining_tickets = datum.getRemaining_tickets();
                            if (remaining_tickets.equals("0")) {
                                mBookEvent.setVisibility(View.GONE);
                            }
                            refundTickets.clear();
                            refundBookedTickets.clear();
                            for (int i = 0; i < datum.getTotalTicketsUser().size(); i++) {
                                RetroGetEventData.TotalTicketsUser totalTicketsUser = datum.getTotalTicketsUser().get(i);
                                bookedtictetdata.add(totalTicketsUser);
                                refundTickets.add(totalTicketsUser.getTicketType());
                                refundBookedTickets.add(String.valueOf(totalTicketsUser.getTotalTickets()));
                            }

                            if (eventType != null) {
                                if (eventType.equals("fav")) {

                                }
                            }

                            if (loginUserId.equals(hostUserID)) {
                                addtoFavCheck_box.setVisibility(View.GONE);
                            }

                            if (event_status.equals("past")) {
//                                attendeelayout.setVisibility(View.GONE);
//                                users.setVisibility(View.GONE);
                                mBookEvent.setVisibility(View.GONE);
//                                eventdistance.setVisibility(View.GONE);
                                invite_layouit.setVisibility(View.GONE);
                                peoplegoing.setVisibility(View.GONE);
                                remainingTicketTV.setVisibility(View.GONE);
                                dashline2.setVisibility(View.GONE);
                                book_location.setClickable(false);

                                if (!freeEvent.equals("0")) {

                                    Calendar c = Calendar.getInstance();

                                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    Date date = null;
                                    try {
                                        date = formatter.parse(formatter.format(c.getTime()));
                                        Log.d("TodayDate", String.valueOf(date.getTime()));

                                        long currentdatetime = date.getTime();
                                        long enddatetime = Long.parseLong(datum.getEventEndDt());


                                        long diff = currentdatetime - enddatetime;
                                        long hours = 172800000;
                                        if (diff > hours) {
                                            askforrefund.setVisibility(View.GONE);
                                        } else {
                                            askforrefund.setVisibility(View.VISIBLE);
                                        }
                                        Log.d("eventdatetime", datum.getEventEndDt() + "  " + currentdatetime + "  " + diff);

//                                System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }


                            getTickets_booked_by_user = datum.getTickets_booked_by_user();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                if (currenlng != null && currentlat != null) {
                                    distance(Double.parseDouble(currentlat), Double.parseDouble(currenlng), Double.parseDouble(eventlat), Double.parseDouble(eventlng));
                                }

                            } else {
                                eventdistance.setVisibility(View.GONE);
                            }

                            postcode = datum.getPostcode();
                            city = datum.getCity();
                            tickets_booked_by_user = datum.getTickets_booked_by_user();
                            if (tickets_booked_by_user.equals("2")) {
                                mBookEvent.setVisibility(View.GONE);
                            }

                            if (!tickets_booked_by_user.equals("0")) {
                                peoplegoing.setVisibility(View.VISIBLE);
                                book_location.setTextColor(getResources().getColor(R.color.white));
                                book_location.setPaintFlags(book_location.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                book_location.setShadowLayer(0, 0, 0, getResources().getColor(R.color.colorTrans));
                                book_location.setTypeface(null, Typeface.BOLD);

                                book_location.setText(location + ", " + postcode);
                                book_location.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = "https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lng + "&travelmode=driving";
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                book_location.setText(location);
                            }

                            gender = datum.getAttendeesGender();
                            hostUserID = datum.getUserid().toString();// host id
                            andendeenumber = datum.getAttendeesNo();

                            if (eventType != null) {
                                if (eventType.equals("upcoming")) {
                                   /* if (freeEvent.equals("0")) {
                                        askforrefund.setVisibility(View.GONE);
                                    } else {
                                        askforrefund.setVisibility(View.VISIBLE);
                                    }*/
                                } else if (eventType.equals("past") || eventType.equals("fav")) {

                                    Calendar c = Calendar.getInstance();

                                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    Date date = null;
                                    try {
                                        date = formatter.parse(formatter.format(c.getTime()));
                                        Log.d("TodayDate", String.valueOf(date.getTime()));

                                        long currentdatetime = date.getTime();
                                        long enddatetime = Long.parseLong(datum.getEventEndDt());


                                        long diff = enddatetime - currentdatetime;
                                        Log.d("eventdatetime", datum.getEventEndDt() + "  " + currentdatetime + "  " + diff);
                                        if (diff >= 1) {
                                            if (datum.getTickets_booked_by_user().equals("0") || datum.getTickets_booked_by_user().equals("1")) {
                                                mBookEvent.setVisibility(View.VISIBLE);
                                            } else {
                                                mBookEvent.setVisibility(View.GONE);
                                                // peoplegoing.setVisibility(View.GONE);
                                            }
                                        } else {
                                            mBookEvent.setVisibility(View.GONE);
                                            peoplegoing.setVisibility(View.GONE);
                                        }
//                                System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }


                            ticktType = datum.getTicketType();
                            transaction_id = datum.getTransactionId();

                            usertype = datum.getUserType();

                            tickettypes.clear();
                            ticketPending.clear();
                            //Todo: get tickets types

                            for (int i = 0; i < datum.getTicketsType().size(); i++) {
                                tickettypes.add(datum.getTicketsType().get(i).getTicketType());
                                ticketprice.add(datum.getTicketsType().get(i).getValue());
                                RetroGetEventData.TicketsType ticketsType = datum.getTicketsType().get(i);

                                ticketPending.add(ticketsType);
                                if (datum.getTicketsType().get(i).getPending_tickets().equals("0") || datum.getTicketsType().get(i).getPending_tickets() == 0) {
                                    tickettypes.remove(i);
                                    ticketprice.remove(i);
                                    ticketPending.remove(i);
                                }
                                Log.d("types", "" + datum.getTicketsType().get(i).getPending_tickets());

                            }


                            if (datum.getPrice() != null) {
                                ticktprice = datum.getPrice();
                            } else if (datum.getTicketsType().size() > 0) {
                                ticktprice = "Paid";
                            }

                            follow_status = String.valueOf(datum.getFollowStatus());
                            event_book = datum.getEventBook();
                            if (follow_status.equals("1")) {
                                follow_button.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart_filled));
                            }

                            numberoftickts = datum.getNoOfTickets();
                            username = datum.getBEventHostname();
                            InviteFriend = datum.getAttendeesGender();
                            booklink1 = datum.getLink1();
                            booklink2 = datum.getLink2();
                            booklink3 = datum.getLink3();

                            book_categry.setText(cate);
                            title = datum.getTitle();
                            timefrom = datum.getTimeFrom();
                            timeto = datum.getTimeTo();
                            hostname = datum.getHostname();
                            if (hostname != null) {
                                String[] name = hostname.split(" ");
                                if (name.length == 1) {
                                    String Fname = name[0];
                                    organiserName.setText(Fname + " ");
                                } else {
                                    String Fname = name[0];
                                    String Lname = name[1];
                                    organiserName.setText(Fname + " " + Lname.subSequence(0, 1));
                                }
                            } else {
                                organiserName.setText(hostname);
                            }
                            host_image = datum.getHostImage();
                            image = (ArrayList<String>) datum.getFile().get(0).getImg();
//                            Collections.reverse(image);


                            if (ticktprice.equals("0")) {
                                eventprice.setText("Free");
                                tickettypedialog = eventprice.getText().toString();
                            } else if (!ticktprice.equals("0") && !ticktprice.equals("Paid")) {
                                eventprice.setText("£ " + ticktprice);
                                tickettypedialog = eventprice.getText().toString();
                            } else {
                                eventprice.setText(ticktprice);
                                tickettypedialog = eventprice.getText().toString();
                            }
                            if (!datum.getLink1().isEmpty()) {
                                SpannableString content = new SpannableString(datum.getLink1());
                                content.setSpan(new UnderlineSpan(), 0, datum.getLink1().length(), 0);
                                link1.setText(content);
                                link1.setVisibility(View.VISIBLE);
                            }
                            if (!datum.getLink2().isEmpty()) {
                                SpannableString content = new SpannableString(datum.getLink2());
                                content.setSpan(new UnderlineSpan(), 0, datum.getLink2().length(), 0);
                                link2.setText(content);
                                link2.setVisibility(View.VISIBLE);
                            }
                            if (!datum.getLink3().isEmpty()) {
                                SpannableString content = new SpannableString(datum.getLink3());
                                content.setSpan(new UnderlineSpan(), 0, datum.getLink3().length(), 0);
                                link3.setText(content);
                                link3.setVisibility(View.VISIBLE);
                            }

                            fav_id = datum.getFavourite().toString();
                            Glide.with(BookEventActivity.this).load(host_image).into(host_pic);

                            //get Time to in AM PM

                            String time_t = Util.convertTimeStampToTime1(Long.parseLong(datum.getEventStartDt())).replaceFirst("a.m.", "am").replaceFirst("p.m.", "pm").replaceFirst("AM", "am").replaceFirst("PM", "pm");
                            String time_to = Util.convertTimeStampToTime1(Long.parseLong(datum.getEventEndDt())).replaceFirst("a.m.", "am").replaceFirst("p.m.", "pm").replaceFirst("AM", "am").replaceFirst("PM", "pm");
                            String start_date = Util.convertTimeStampDate(Long.parseLong(datum.getEventStartDt()));
                            String end_date = Util.convertTimeStampDate(Long.parseLong(datum.getEventEndDt()));

                            Log.d("timecheck", time_t + "  " + time_to);
                            Calendar c = Calendar.getInstance();

                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date date = null;
                            try {
                                date = formatter.parse(formatter.format(c.getTime()));
                                Log.d("TodayDate", date.getTime() + datum.getEventEndDt());
                                String formattedDate = String.valueOf(date.getTime());

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (start_date.matches(end_date)) {
                                book_date.setText(start_date);
                            } else {
                                book_date.setText(start_date + " - " + end_date);
                            }

                            String startTime = removeLeadingZeroes(time_t);
                            if (startTime.contains(":00")) {
                                startTime = removeLeadingZeroes(time_t).replace(":00", "");
                                book_time.setText(startTime);
                            } else {
                                book_time.setText(removeLeadingZeroes(time_t));
                            }

                            String endTime = removeLeadingZeroes(time_to);
                            if (endTime.contains(":00")) {
                                endTime = removeLeadingZeroes(time_to).replace(":00", "");
                                book_time2.setText(endTime);
                            } else {
                                book_time2.setText(removeLeadingZeroes(endTime));
                            }
//                            ------------------------------------------------------------------

                            int count = Integer.parseInt(String.valueOf(datum.getTotalEventBookings()));
                            int lesscount = count - 1;
                            if (event_book.equals("1") && count == 1) {
                                peoplegoing.setText("You are going");
                            } else if (event_book.equals("1") && count == 2) {
                                peoplegoing.setText("You and " + lesscount + " other person is going");
                            } else if (event_book.equals("1") && count > 2) {
                                peoplegoing.setText("You and " + lesscount + " other people are going");
                            } else if (event_book.equals("0") && count == 1) {
                                peoplegoing.setText("1 person is going");
                            } else if (event_book.equals("0") && count > 1) {
                                peoplegoing.setText(count + " people are going");
                            }


                            //Check Event Fav Status
                            if (fav_id.equals("1")) {
                                addtoFavCheck_box.setChecked(true);
                            } else if (fav_id.equals("0")) {
                                addtoFavCheck_box.setChecked(false);
                            }
                            viewPager.setOffscreenPageLimit(1);
                            pagerAdapter = new B_EventDetailImageAdapter(BookEventActivity.this, image);
                            viewPager.setAdapter(pagerAdapter);
                            Log.d("image", "" + image);

                            addBottomDots(0);
                            //indicator.setViewPager(viewPager);
                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                                @Override
                                public void onPageScrolled(int i, float v, int i1) {
                                    CurrentPage = i;
                                }

                                @Override
                                public void onPageSelected(int i) {
                                    addBottomDots(i);
                                }

                                @Override
                                public void onPageScrollStateChanged(int i) {

                                    if (i == ViewPager.SCROLL_STATE_IDLE) {

                                        int pagecount = image.size();

                                        if (CurrentPage == pagecount) {
                                            viewPager.setCurrentItem(pagecount, true);
                                        } else
                                            CurrentPage++;
                                    }

                                }
                            });

                            descri.setText(decs);
//                            book_location.setPaintFlags(book_location.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
//                            book_location.setShadowLayer(3,2,2,getResources().getColor(R.color.shadowColor));

                            event_title.setText(title);
                            invitefriends.setText("Invite Friends " + "( " + InviteFriend + " )");



                            /*id_code*/

                            if (loginUserId.equals(hostUserID)) {
                                book_message.setVisibility(View.INVISIBLE);
                                organiser_layout.setVisibility(View.GONE);
//                                dashline2.setVisibility(View.GONE);
                                messagelayout.setVisibility(View.GONE);
                                mBookEvent.setVisibility(View.GONE);
                            } else if (hostUserID == (null)) {
                                book_message.setVisibility(View.INVISIBLE);
                            } else {
                                book_message.setVisibility(View.VISIBLE);
//                                dashline2.setVisibility(View.GONE);
                            }
                            Log.d("Check_User", "" + loginUserId + "  " + hostUserID);
                        }
                    } else if (response.body().getStatus().equals("400")) {
                        Toast.makeText(BookEventActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } else {
                    Intent intent = new Intent(BookEventActivity.this, NoInternetScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<RetroGetEventData> call, Throwable t) {
                Log.d("events1", "" + t.getMessage() + " " + value);
                Toast.makeText(BookEventActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    String removeLeadingZeroes(String s) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() > 0 && sb.charAt(0) == '0') {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }


    public void addFav(final String token, final String value) {

        addtoFavCheck_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    if (!fav_id.contains("1")) {
                        Call<NormalRetroFav> call = WebAPI.getInstance().getApi().fav("Bearer " + token, "application/json", value);
                        call.enqueue(new Callback<NormalRetroFav>() {
                            @Override
                            public void onResponse(Call<NormalRetroFav> call, Response<NormalRetroFav> response) {
                                if (response.body() != null) {

                                }
                                //Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<NormalRetroFav> call, Throwable t) {

                            }
                        });
                    }

                } else {
                    Call<NormalRetrodeleteFav> call = WebAPI.getInstance().getApi().deletefav("Bearer " + S_token, "application/json", value);
                    call.enqueue(new Callback<NormalRetrodeleteFav>() {
                        @Override
                        public void onResponse(Call<NormalRetrodeleteFav> call, Response<NormalRetrodeleteFav> response) {
                            if (response.body() != null) {

                            }
                            //  Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<NormalRetrodeleteFav> call, Throwable t) {

                        }
                    });
                }
            }
        });

    }


    //add dots at bottom
    private void addBottomDots(int currentPage) {

        dots = new TextView[image.size()];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {

            dots[i] = new TextView(BookEventActivity.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(50);
            dots[i].setTextColor(getResources().getColor(R.color.colortextwhite));
            dotsLayout.addView(dots[i]);

        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorTheme));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        String distance = String.valueOf(dist);
        int roundVal = (int) Math.round(dist);
        Log.d("distance", String.valueOf(roundVal));
        if (roundVal == 1) {
            eventdistance.setText(roundVal + " mile away");
        } else {
            eventdistance.setText(roundVal + " miles away");
        }

        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
