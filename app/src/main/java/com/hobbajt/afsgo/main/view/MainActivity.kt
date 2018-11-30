package com.hobbajt.afsgo.main.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.hobbajt.afsgo.R
import com.hobbajt.afsgo.main.FragmentsChanger
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, MainContractor.View
{
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var fragmentsChanger: FragmentsChanger

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        setContentView(R.layout.activity_main)
    }

    override fun start()
    {
        fragmentsChanger.onStart()
    }

    override fun onResume()
    {
        super.onResume()
        presenter.onViewReady()
    }

    override fun onBackPressed()
    {
        if (!fragmentsChanger.onBackPressed())
        {
            super.onBackPressed()
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        presenter.detachView()
    }

    override fun closeApp()
    {
        finishAffinity()
    }

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector
}
