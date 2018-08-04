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
import android.widget.CompoundButton
import android.widget.Toast
import com.medexpertz.medexpertzdoctor.PrescriptionFragmentBinding
import com.medexpertz.medexpertzdoctor.R
import com.medexpertz.medexpertzdoctor.etc.common.BaseFragment
import com.medexpertz.medexpertzdoctor.etc.model.Status
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 02 May 2018 at 10:42 AM
 */
class PrescriptionFragment: BaseFragment(), PrescriptionAdapter.OnItemClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    @Inject lateinit var mFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: PrescriptionViewModel
    private lateinit var mBinding: PrescriptionFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_prescription, container, false)
        ItemTouchHelper(RecyclerItemTouchHelper(this)).attachToRecyclerView(mBinding.prescriptionRV)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(activity!!, mFactory)[PrescriptionViewModel::class.java]

        initSearch()
        initPrescription()
        addOrEditPrescription()

        mBinding.handler = this
        mBinding.editPosition = -1
        mBinding.searchET.clearSearchFocus()
    }

    private fun addOrEditPrescription() {
        mViewModel.updatePrescription.observe(this, Observer {
            mBinding.status = it?.status

            if (it?.status == Status.SUCCESS) {
                mBinding.editPrescription = false
                mBinding.adapter!!.addItem(it.data!!, mBinding.editPosition!!)
                Toast.makeText(context, R.string.medicine_updated, Toast.LENGTH_SHORT).show()
            }
        })

        mViewModel.deletePrescription.observe(this, Observer {
            mBinding.status = it?.status

            if (it?.status == Status.SUCCESS) {
                Toast.makeText(context, R.string.medicine_deleted, Toast.LENGTH_SHORT).show()
            }

            if (it?.status == Status.ERROR) {
                mBinding.adapter?.addItem(it.data!!, -1)
            }
        })
    }

    private fun initPrescription() {
        mViewModel.getAppointmentDetails().observe(this, Observer {
            mBinding.status = it?.status
            if (it?.status == Status.SUCCESS) {
                mBinding.adapter = PrescriptionAdapter(it.data!!.medicines!!, this)
            }
        })
    }

    private fun initSearch() {
        mViewModel.medicines.observe(this, Observer {
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
                .doOnNext { mViewModel.searchMedicine(it!!.toString()) }
                .subscribe()
        mBinding.searchET.setOnBindSuggestionCallback { _, _, textView, item, _ ->
            textView.text = item.body
            textView.setOnClickListener {
                mBinding.prescription = Prescription(item as Medicine, mViewModel.appointment)
                mBinding.editPrescription = true
                mBinding.searchET.clearSearchFocus()
            }
        }
    }

    fun whenceChecked(buttonView: CompoundButton, isChecked: Boolean, prescription: Prescription) {
        if (!isChecked) return
        if (buttonView.id == R.id.afterCB) {
            prescription.afterFood = 1
            prescription.beforeFood = 0
            mBinding.beforeCB.isChecked = false
        } else {
            prescription.afterFood = 0
            prescription.beforeFood = 1
            mBinding.afterCB.isChecked = false
        }
    }

    fun addPrescription(prescription: Prescription) {
        if (prescription.isValid()) {
            mViewModel.addPrescription(prescription)
        } else Toast.makeText(context, R.string.incomplete_prescription, Toast.LENGTH_SHORT).show()
    }

    fun apply() {
        mViewModel.updateState(PrescriptionState.FINISHED)
    }

    override fun onPrescription(prescription: Prescription, position: Int) {
        mBinding.prescription = prescription
        mBinding.editPosition = position
        mBinding.editPrescription = true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        val prescription = mBinding.adapter!!.deleteItemAtPosition(position)
        mViewModel.deletePrescription(prescription)
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 400L
    }
}