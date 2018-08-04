package com.medexpertz.medexpertzdoctor.prescription;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.medexpertz.medexpertzdoctor.R;


/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 05 Nov 2017 at 5:41 PM
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
	private RecyclerItemTouchHelperListener listener;
	
	public RecyclerItemTouchHelper(RecyclerItemTouchHelperListener listener) {
		super(0, (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT));
		this.listener = listener;
	}
	
	@Override
	public int convertToAbsoluteDirection(int flags, int layoutDirection) {
		return super.convertToAbsoluteDirection(flags, layoutDirection);
	}
	
	@Override
	public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
		return true;
	}
	
	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
		listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
	}
	
	@Override
	public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
		if (viewHolder != null) {
			final View foregroundView = viewHolder.itemView.findViewById(R.id.foregroundCL);
			getDefaultUIUtil().onSelected(foregroundView);
		}
	}
	
	@Override
	public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
		final View foregroundView = viewHolder.itemView.findViewById(R.id.foregroundCL);
		getDefaultUIUtil().clearView(foregroundView);
	}
	
	@Override
	public void onChildDraw(Canvas c, RecyclerView recyclerView,
	                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
	                        int actionState, boolean isCurrentlyActive) {
		final View foregroundView = viewHolder.itemView.findViewById(R.id.foregroundCL);
		
		getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
		    actionState, isCurrentlyActive);
	}
	
	@Override
	public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
	                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
	                            int actionState, boolean isCurrentlyActive) {
		final View foregroundView = viewHolder.itemView.findViewById(R.id.foregroundCL);
		getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
		    actionState, isCurrentlyActive);
	}
	
	public interface RecyclerItemTouchHelperListener {
		void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
	}
}