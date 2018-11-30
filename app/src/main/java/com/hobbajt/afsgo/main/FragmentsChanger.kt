package com.hobbajt.afsgo.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.hobbajt.afsgo.R
import com.hobbajt.afsgo.core.mvp.MVPFragment
import com.hobbajt.afsgo.taskslist.view.TasksListFragment

class FragmentsChanger(private val fragmentManager: FragmentManager)
{
    private val currentFragment: Fragment?
        get() = fragmentManager.findFragmentById(R.id.fragment)

    fun changeFragment(fragment: MVPFragment<*>, addToBackStack: Boolean)
    {
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
        currentFragment?.let {
            transaction.remove(it)
        }
        transaction.add(R.id.fragment, fragment)

        if (addToBackStack)
        {
            transaction.addToBackStack(fragment::class.java.simpleName)
        }

        transaction.commitAllowingStateLoss()
    }

    fun onBackPressed(): Boolean
    {
        if (fragmentManager.backStackEntryCount > 0)
        {
            fragmentManager.popBackStackImmediate()
            return true
        }
        return false
    }

    fun onStart()
    {
        if (currentFragment == null)
        {
            changeFragment(TasksListFragment.newInstance(), false)
        }
    }

    fun goBack()
    {
        fragmentManager.popBackStack()
    }
}