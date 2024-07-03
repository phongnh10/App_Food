package adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.du_an_1.R;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import dao.UserDAO;
import model.Shop;
import model.User;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdapterViewHolder> {
    private List<User> userList;
    private Context context;
    private UserDAO userDAO;

    public AdminAdapter(List<User> userList, Context context, UserDAO userDAO) {
        this.userList = userList;
        this.context = context;
        this.userDAO = userDAO;
    }

    @NonNull
    @Override
    public AdminAdapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_user, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.AdapterViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        private CardView cv_manage_user;
        private TextView getTxtIdUser;
        private TextView txt_user;
        private TextView txt_pass;
        private TextView txt_name;
        private TextView txt_phone;
        private TextView txt_cccd;
        private TextView txt_role;


        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_manage_user = itemView.findViewById(R.id.cv_manage_user);
            txt_name = itemView.findViewById(R.id.txt_name_user);
            txt_phone = itemView.findViewById(R.id.txt_phone_user);
            txt_cccd = itemView.findViewById(R.id.txt_cccd_user);
            txt_role = itemView.findViewById(R.id.txt_role_user);

            cv_manage_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        User user = userList.get(position);
                        showUser(user);
                    }
                }
            });
        }

        public void bind(User user) {
            String role = "";
            if (user.getRole() == 0) {
                role = "Admin";
            } else if (user.getRole() == 1) {
                role = "Seller";
            } else if (user.getRole() == 2) {
                role = "Buyer";
            }
            txt_name.setText(user.getName());
            txt_phone.setText(String.valueOf(user.getPhone()));
            txt_cccd.setText(String.valueOf(user.getCccd()));
            txt_role.setText(role);
        }

        //dialog user
        private void showUser(User user) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_information_user, null);
            builder.setView(dialogView);
            ///
            EditText edt_id_user = dialogView.findViewById(R.id.edt_id_user);
            EditText edt_user = dialogView.findViewById(R.id.edt_user);
            EditText edt_pass = dialogView.findViewById(R.id.edt_pass);
            EditText edt_name = dialogView.findViewById(R.id.edt_name);
            EditText edt_phone = dialogView.findViewById(R.id.edt_phone);
            EditText edt_cccd = dialogView.findViewById(R.id.edt_cccd);
            EditText edt_role = dialogView.findViewById(R.id.edt_role);

            Button btn_delete1 = dialogView.findViewById(R.id.btn_delete);

            // set edt
            edt_id_user.setEnabled(false);
            edt_user.setEnabled(false);
            edt_pass.setEnabled(false);
            edt_name.setEnabled(false);
            edt_phone.setEnabled(false);
            edt_cccd.setEnabled(false);
            // check role
            int role = getRoleFromSharedPreferences();
            if (role == user.getRole()) {
                edt_role.setEnabled(false);
                btn_delete1.setEnabled(false);
            }

            edt_role.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        showConfirmationDialogRole();
                    }
                }
            });


            edt_id_user.setText(String.valueOf(user.getIdUser()));
            edt_user.setText((user.getUser()));
            edt_pass.setText((user.getPass()));
            edt_name.setText(user.getName());
            edt_phone.setText(String.valueOf(user.getPhone()));
            edt_cccd.setText(String.valueOf(user.getCccd()));
            edt_role.setText(String.valueOf(user.getRole()));


            //
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            // back diaglog
            ImageView iv_Back = dialogView.findViewById(R.id.iv_back);
            iv_Back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            //update user
            Button btn_update = dialogView.findViewById(R.id.btn_update);
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int idUser = user.getIdUser();
                    String user = edt_user.getText().toString();
                    String pass = edt_pass.getText().toString();
                    String name = edt_name.getText().toString();
                    String phoneS = edt_phone.getText().toString();
                    String cccdS = edt_cccd.getText().toString();
                    String roleS = edt_role.getText().toString();

                    // check role not int
                    try {
                        long phone = Long.parseLong(phoneS);
                        long cccd = Long.parseLong(cccdS);
                        int role = Integer.parseInt(roleS);

                        //
                        if (user.isEmpty() || pass.isEmpty() || name.isEmpty() || roleS.isEmpty()) {
                            Toast.makeText(context, "Import full information", Toast.LENGTH_SHORT).show();
                        } else if (role < 1 || role > 2) {
                            Toast.makeText(context, "Role must be is 1 or 2", Toast.LENGTH_SHORT).show();
                        } else {
                            final User user1 = new User(idUser, user, pass, name, phone, cccd, role);

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Notification");
                            builder.setMessage("Do you want to continue updating?");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // update
                                    boolean check = userDAO.upDateUser(user1);
                                    if (check) {
                                        Toast.makeText(context, "Edited successfully", Toast.LENGTH_SHORT).show();
                                        // Load data
                                        userList.clear();
                                        userList.addAll(userDAO.getListUser());
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(context, "Edit failed", Toast.LENGTH_SHORT).show();
                                    }
                                    // exit dialog
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // exit dialog
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Role must be an integer", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Button btn_delete = dialogView.findViewById(R.id.btn_delete);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteDialog(user);
                    alertDialog.dismiss();

                }
            });


        }

        //
        private void showConfirmationDialogRole() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Notification");
            builder.setMessage("Do you want to continue? \n" +
                    "When changing roles, user rights will change\n" +
                    "Please pay attention !");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Canel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        private void showConfirmationDialogDelete(User user) {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
            builder.setTitle("Notification");
            builder.setMessage("Do you want to continue? \n" +
                    "Once deleted, the account cannot be restored\n"+
                    "Please pay attention !");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean check = userDAO.deleteUser(user.getIdUser());
                    if (check) {
                        userList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            });
            builder.setNegativeButton("Canel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            androidx.appcompat.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        //delete user
        private void showDeleteDialog(User user) {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Notification");
            builder.setMessage("Do you want to delete user \"" + user.getUser() + "\" with idUser \"" + user.getIdUser() + "\"?");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    showConfirmationDialogDelete(user);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            android.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        // get role
        public int getRoleFromSharedPreferences() {
            SharedPreferences sharedPreferences = context.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
            return sharedPreferences.getInt("role", -1);
        }
    }

}