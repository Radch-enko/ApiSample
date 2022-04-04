package com.api.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.api.sample.databinding.ActivityMainBinding;
import com.api.sample.network.ApiHandler;
import com.api.sample.network.ErrorUtils;
import com.api.sample.network.models.LoginBody;
import com.api.sample.network.service.ApiService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ApiService service = ApiHandler.getInstance().getService();
    CompositeDisposable disposableBag = new CompositeDisposable();
    ErrorUtils errorHandler = new ErrorUtils();
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.buttonRegister.setOnClickListener(view -> {
            doLogin();
        });
        setContentView(binding.getRoot());
    }

    private void doLogin() {
        Disposable dispose = service.doLogin(getLoginData())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    Toast.makeText(getApplicationContext(), "Авторизация прошла успешно! Держи свой токен: " + loginResponse.getToken(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    errorHandler.handle(throwable, message -> {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    }, message -> {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    });
                });

        disposableBag.add(dispose);
    }

    private LoginBody getLoginData() {
        return new LoginBody(binding.editEmail.getText().toString(), binding.editPassword.getText().toString());
    }

    @Override
    protected void onDestroy() {
        disposableBag.dispose();
        super.onDestroy();
    }
}