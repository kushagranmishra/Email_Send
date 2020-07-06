package kushagra.digipodium.email_send;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment {
    private Button btnSend;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText editEmail = view.findViewById(R.id.editEmail);
        final EditText editBody = view.findViewById(R.id.editBody);
        final EditText editSubject = view.findViewById(R.id.editSubject);

        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String subject = editSubject.getText().toString();
                String body = editBody.getText().toString();
                if (email.length() == 0) {
                    Snackbar.make(btnSend, "Email is Invalid", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (body.length() == 0) {
                    Snackbar.make(btnSend, "Email message is not provided", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (subject.length() == 0) {
                    Snackbar.make(btnSend, "please add a subject for email", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String[] addresses = email.split(",");
                composeEmail(addresses, subject, body);
            }
        });
        Button btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        Button btnContact = view.findViewById(R.id.btnContact);
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_thirdFragment2);
            }
        });
    }
    public void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Snackbar.make(btnSend, "No application can perform this task", Snackbar.LENGTH_LONG).show();
        }
        }

}
