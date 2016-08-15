package com.example.user.simpleui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DrinkOrderDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DrinkOrderDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrinkOrderDialog extends DialogFragment {//繼承
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    NumberPicker mNumberPicker;
    NumberPicker INumberPicker;
    RadioGroup iceRadioGroup;
    RadioGroup sugarRadioGroup;
    EditText noteEditText;

    private  DrinkOrder drinkOrder;

    private OnFragmentInteractionListener mListener;

    public DrinkOrderDialog() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrinkOrderDialog.
     */
    // TODO: Rename and change types and number of parameters
    public static DrinkOrderDialog newInstance(DrinkOrder drinkOrder) {
        DrinkOrderDialog fragment = new DrinkOrderDialog();
        Bundle args = new Bundle();//會有我需要攜帶的變數
        args.putParcelable(ARG_PARAM1,drinkOrder);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {//從activity拿到資料
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {//子頁面長怎樣
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_drink_order_dialog, container, false);
//    }

    public Dialog onCreateDialog(Bundle saveedInstanceState)
    {

        if(getArguments() != null)
        {
           this.drinkOrder = getArguments().getParcelable(ARG_PARAM1);
        }

        View contentView = getActivity().getLayoutInflater().inflate(R.layout.fragment_drink_order_dialog,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(contentView)
                .setTitle(drinkOrder.drink.name)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drinkOrder.mNumber = mNumberPicker.getValue();
                        drinkOrder.INumber = INumberPicker.getValue();
                        drinkOrder.ice = getSelectedTextFromRadioGroup(iceRadioGroup);
                        drinkOrder.suger = getSelectedTextFromRadioGroup(sugarRadioGroup);
                        drinkOrder.note = noteEditText.getText().toString();

                        if(mListener != null)
                        {
                            mListener.onDrinkOrderResult(drinkOrder);
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        mNumberPicker = (NumberPicker)contentView.findViewById(R.id.MnumberPicker);
        INumberPicker =  (NumberPicker)contentView.findViewById(R.id.LnumberPicker);
        iceRadioGroup = (RadioGroup)contentView.findViewById(R.id.iceradioGroup);
        sugarRadioGroup = (RadioGroup)contentView.findViewById(R.id.sugarradioGroup);
        noteEditText = (EditText)contentView.findViewById(R.id.noteEditText);


        mNumberPicker.setMaxValue(100);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setValue(drinkOrder.mNumber);

        INumberPicker.setMaxValue(100);
        INumberPicker.setMinValue(0);
        INumberPicker.setValue(drinkOrder.INumber);

        noteEditText.setText(drinkOrder.note);
        setSelectedTextInRadioGroup(drinkOrder.ice,iceRadioGroup);
        setSelectedTextInRadioGroup(drinkOrder.suger,sugarRadioGroup);

        return builder.create();
    }

    private String getSelectedTextFromRadioGroup(RadioGroup radioGroup)
    {
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton= (RadioButton)radioGroup.findViewById(id);
        return radioButton.getText().toString();
    }

    private  void setSelectedTextInRadioGroup(String selectedText, RadioGroup radioGroup)
    {
        int count = radioGroup.getChildCount();
        for(int i =0; i<count ;i++)
        {
            View view = radioGroup.getChildAt(i);
            if(view instanceof  RadioButton)
            {
                RadioButton radioButton = (RadioButton)view;
                if(radioButton.getText().toString().equals(selectedText))
                {
                    radioButton.setChecked(true);
                }
                else
                {
                    radioButton.setChecked(false);
                }
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {//到底有沒有這個人存在
//            mListener.onDrinkOrderResult(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {//context等於activity 溝通橋樑
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;//是一個會介面的人
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {//自動釋放掉
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {//定義介面，下面有很多事情，要會做才能溝通
        // TODO: Update argument type and name
        void onDrinkOrderResult(DrinkOrder drinkOrder);
    }
}
