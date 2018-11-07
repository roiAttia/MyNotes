package roiattia.com.mynotes.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.List;

import roiattia.com.mynotes.database.folder.FolderEntity;

public class RadioButtonsDialog extends DialogFragment {

    private static final String TAG = RadioButtonsDialog.class.getSimpleName();
    private String[] mOptionsList;
    private SortDialogListener mListener;
    public String mTitle;

    public interface SortDialogListener {
        void onDialogOptionClick(int whichSelected);
        void onDialogCreateNewFolder();
    }

    public void setSortOptions(String[] optionsList){
        mOptionsList = optionsList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(mTitle)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setItems(mOptionsList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogOptionClick(which);
                        dismiss();
                    }
                })
                .setPositiveButton("new folder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogCreateNewFolder();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
        });

        return dialog.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the FoldersDialogCallback to send events to the host
            mListener = (SortDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement FoldersDialogCallback");
        }
    }
}
