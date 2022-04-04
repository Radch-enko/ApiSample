package com.api.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.api.sample.databinding.ActivityMainBinding;
import com.api.sample.network.ApiHandler;
import com.api.sample.network.ErrorUtils;
import com.api.sample.network.service.ApiService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ApiService service = ApiHandler.getInstance().getService();

    CompositeDisposable disposableBag = new CompositeDisposable(); /* воспринимайте это как сумку куда мы кладем все наши сетевые запросы,
                                                                    и выбрасываем эту сумку когда наша Activity закрыта */

    ErrorUtils errorHandler = new ErrorUtils(); // Наш обработчик ошибок
    ActivityMainBinding binding; // Это объект который содержит в себе все View из activity_main.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // Инициализурем наш binding

        Disposable dispose = service.getCountries()
                .subscribeOn(Schedulers.computation()) // Выбираем где будет выполнять запрос
                .observeOn(AndroidSchedulers.mainThread()) /* Выбираем на каком потоке будем обрабатывать результат,
                                                              в данном случае на main потоке, так как мы хотим менять UI*/

                // В subscribe передается две лямбды: если все выполнилось успешно и если произошла ошибка
                .subscribe(response -> {
                    binding.list.setAdapter(new CountriesAdapter(this, response));
                }, throwable -> {
                    errorHandler.handle(throwable, message -> {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    }, message -> {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    });
                });

        // Кладем в сумку наш запрос
        disposableBag.add(dispose);

        setContentView(binding.getRoot());
    }


    @Override
    protected void onDestroy() {
        // Очищаем нашу сумку, чтобы после закрытия приложения не висели в бэкграунде
        disposableBag.dispose();
        super.onDestroy();
    }
}