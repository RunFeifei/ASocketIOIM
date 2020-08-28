package com.run.asocketioim.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.run.asocketioim.widget.LoadingDialog

/**
 * Created by PengFeifei on 2018/10/27.
 */

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity(), IBaseView {
    protected lateinit var viewModel: T
    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        window.setFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        viewModel = initViewModel()
        progressDialog = initProgressDialog()
        viewModel.apiLoading.observe(this, Observer<Boolean> {
            if (it == null) {
                return@Observer
            }
            if (it) showLoading() else hideLoading()
        })

        viewModel.toastLiveData.observe(this, Observer<String> {
            it?.apply {
                showToast(it)
            }
        })

        viewModel.apiException.observe(this, Observer<Throwable> { throwable ->
            if (throwable == null) {
                return@Observer
            }
            if (handleException(throwable)) {
                return@Observer
            }
        })
        initPage(savedInstanceState)
        initLivedata(viewModel)
    }


    protected abstract fun initViewModel(): T
    protected abstract fun layoutId():Int
    protected abstract fun initPage(savedInstanceState: Bundle?)
    protected abstract fun initLivedata(viewModel: T)

    /*****************************************************************************/

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun showLoading() {
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    override fun handleLogin() {

    }

    override fun handleException(throwable: Throwable): Boolean {
        Log.e("BaseViewModel--> ", throwable?.toString() ?: "did not get detail exception")
        return false
    }

    /*****************************************************************************/

    private fun initProgressDialog(): LoadingDialog {
        val progressDialog = LoadingDialog(this)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)
        return progressDialog
    }

}
