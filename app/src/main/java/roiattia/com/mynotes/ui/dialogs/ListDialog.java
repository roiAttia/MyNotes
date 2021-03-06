package roiattia.com.mynotes.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import roiattia.com.mynotes.R;

import static roiattia.com.mynotes.utils.Constants.EMPTY_STRING;

public class ListDialog extends DialogFragment {

    private static final String TAG = ListDialog.class.getSimpleName();
    private String[] mListItemsStrings;
    private ListDialogListener mListener;
    private String mTitle, mMessage, mPositiveBtn, mNegativeBtn, mNeutralBtn;
    private int mCheckedItem;

    public ListDialog(){
        mMessage = EMPTY_STRING;
        mPositiveBtn = EMPTY_STRING;
        mNegativeBtn = EMPTY_STRING;
        mNeutralBtn = EMPTY_STRING;
        mCheckedItem = -1;
    }

    public interface ListDialogListener {
        /**
         * Send selected item position back to the activity
         * @param whichSelected the selected item position
         */
        void onItemSelected(int whichSelected);
        void onPositiveSelected();
        void onNegativeSelected();
        void onNeutralSelected();
    }

    /**
     * Set the list items strings
     * @param itemsStrings list items strings
     */
    public void setListItemsStrings(String[] itemsStrings){
        mListItemsStrings = itemsStrings;
    }

    /**
     * Set the title string for the dialog
     * @param title the string to show in the title
     */
    public void setTitle(String title){
        mTitle = title;
    }

    /**
     * Set the dialog's message
     * @param message the message to show
     */
    public void setMessage(String message){
        mMessage = message;
    }

    public void setButtons(String positiveBtn, String negativeBtn, String neutralBtn){
        mPositiveBtn = positiveBtn;
        mNegativeBtn = negativeBtn;
        mNeutralBtn = neutralBtn;
    }

    public void setCheckedItem(int checkedItem){
        mCheckedItem = checkedItem;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View title = inflater.inflate(R.layout.title_dialog, null, false);
        TextView titleTextView = title.findViewById(R.id.tv_dialog_title);
        titleTextView.setText(mTitle);

        builder.setCustomTitle(title)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(mListItemsStrings, mCheckedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onItemSelected(which);
                        dismiss();
                    }
                });
        if(!mPositiveBtn.equals(EMPTY_STRING)){
            builder.setPositiveButton(mPositiveBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onPositiveSelected();
                }
            });
        }

        if(!mNegativeBtn.equals(EMPTY_STRING)){
            builder.setNegativeButton(mNegativeBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onNegativeSelected();
                }
            });
        }

        if(!mNeutralBtn.equals(EMPTY_STRING)){
            builder.setNeutralButton(mNeutralBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onNeutralSelected();
                }
            });
        }

        if(!mMessage.equals(EMPTY_STRING)){
            builder.setMessage(mMessage);
        }

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the CheckBoxesDialogListener to send events to the host
            mListener = (ListDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement ListDialogListener");
        }
    }
}
