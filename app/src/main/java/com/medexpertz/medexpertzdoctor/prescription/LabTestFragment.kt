package com.medexpertz.medexpertzdoctor.prescription

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.medexpertz.medexpertzdoctor.LabTestFragmentBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseFragment
import com.medexpertz.medexpertzdoctor.etc.model.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 May 2018 at 11:00 AM
 */
class LabTestFragment : BaseFragment(), LabTestAdapter.OnItemClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: PrescriptionViewModel
    private lateinit var mBinding: LabTestFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lab_test, container, false)
        ItemTouchHelper(RecyclerItemTouchHelper(this)).attachToRecyclerView(mBinding.labTestRV)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!, mFactory)[PrescriptionViewModel::class.java]

        initSearch()
        initTabTests()
        addOrEditLabTest()

        mBinding.handler = this
        mBinding.editPosition = -1
    }

    private fun addOrEditLabTest() {
        mViewModel.updateLabTest.observe(this, Observer {
            mBinding.status = it?.status

            if (it?.status == Status.SUCCESS) {
                mBinding.editLabTest = false
                mBinding.adapter!!.addItem(it.data!!, mBinding.editPosition!!)
                Toast.makeText(context, R.string.medicine_updated, Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.deleteLabTest.observe(this, Observer {
            mBinding.status = it?.status

            if (it?.status == Status.SUCCESS) {
                Toast.makeText(context, R.string.lab_test_deleted, Toast.LENGTH_SHORT).show()
            }

            if (it?.status == Status.ERROR) {
                mBinding.adapter?.addItem(it.data!!, -1)
            }
        })
    }

    private fun initTabTests() {
        mViewModel.getAppointmentDetails().observe(this, Observer {
            mBinding.status = it?.status
            if (it?.status == Status.SUCCESS) {
                mBinding.adapter = LabTestAdapter(it.data!!.labtests!!, this)
            }
        })
    }

    private fun initSearch() {
        mBinding.searchET.clearSearchFocus()
        mViewModel.labTests.observe(this, Observer {
            when (it?.status) {
                Status.LOADING -> mBinding.searchET.showProgress()
                Status.ERROR -> mBinding.searchET.hideProgress()
                Status.SUCCESS -> {
                    mBinding.searchET.swapSuggestions(it.data)
                    mBinding.searchET.hideProgress()
                }
            }
        })
        mBinding.searchET.queryChanges(3).skipInitialValue()
                .debounce(DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { mViewModel.searchLabTest(it!!.toString()) }
                .subscribe()
        mBinding.searchET.setOnBindSuggestionCallback { _, _, textView, item, _ ->
            textView.text = item.body
            textView.setOnClickListener {
                mBinding.labTest = item as LabTest
                mBinding.editLabTest = true
                mBinding.searchET.clearSearchFocus()
            }
        }
    }

    fun addLabTest(labTest: LabTest) {
        mViewModel.addLabTest(labTest)
    }

    fun apply() {
        mViewModel.updateState(PrescriptionState.SUMMARY)
    }

    override fun onLabTest(labTest: LabTest, position: Int) {
        mBinding.labTest = labTest
        mBinding.editPosition = position
        mBinding.editLabTest = true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        val labTest = mBinding.adapter!!.deleteItemAtPosition(position)
        mViewModel.deleteLabTest(labTest)
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 400L
    }
}