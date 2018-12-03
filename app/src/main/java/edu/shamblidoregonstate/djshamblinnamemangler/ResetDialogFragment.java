package edu.shamblidoregonstate.djshamblinnamemangler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class ResetDialogFragment extends DialogFragment {


    public ResetDialogFragment() {
        onCreateDialog(null);
    }

    public ResetDialogFragment(Context context){
        onCreateDialog(null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.reset_dialog)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), MangleNameNicely.class);
                        startActivity(intent);
                    }
                })
                .create();
    }
}
