package com.api.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.api.sample.network.ApiHandler;

import com.api.sample.network.ErrorUtils;
import com.api.sample.network.models.LoginBody;
import com.api.sample.network.models.LoginResponse;

import com.api.sample.network.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText editEmail, editPassword;

    ApiService service = ApiHandler.getInstance().getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    private void initializeViews() {
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        findViewById(R.id.buttonRegister).setOnClickListener(view -> {
            doLogin();
        });
    }

    // Пока оставлю так, но я начал писать обертку SafeCaller чтобы просто передавать колбэки
    private void doLogin(){

        AsyncTask.execute(() -> {
            service.doLogin(getLoginData()).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    // onResponse - срабатывает всегда, вне зависимости успешно ли выполнился запрос ( успешное выполение это как правило код 200)
                    // поэтому мы должны проверять успешно ли выполнился запрос
                    // и обработать уже серверные ошибки - 404 Not found, 400 Bad Request и так далее

                    if (response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Авторизация прошла успешно! Держи свой токен: " + response.body().getToken(), Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 400){
                        // 400 - это Bad request, такую ошибку сервер выдает когда мы неправильно пользуемся API
                        // поэтому мы на клиентской стороне должны обработать эту ошибку и показать пользователю
                        // чтобы он понял, что он делает не правильно
                        // к примеру не ввел email или пароль, но пытается залогиниться

                        // Чтобы преобразовать json ошибки в строку мы используем наш класс ErrorUtils
                        String serverErrorMessage = ErrorUtils.parseError(response).message();
                        Toast.makeText(getApplicationContext(), serverErrorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка! Попробуйте позже", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    // в блоке onFailure обрабатываются ошибки, которые не связаны с сервером бэкэнда
                    // например если на устройстве нет доступа в Интернет
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private LoginBody getLoginData() {
        return new LoginBody(editEmail.getText().toString(), editPassword.getText().toString());
    }
}