package drankbank.android.drankbank.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import drankbank.android.drankbank.adapter.EntryAdapter;

/**
 * Created by Veronica on 12/1/16.
 */

public class EntryListTouchHelper extends ItemTouchHelper.Callback {
    private EntryAdapter adapter;

    public EntryListTouchHelper(EntryAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /*
    Allows only swipes for entries
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeFlag(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        adapter.removeEntry(viewHolder.getAdapterPosition());
    }
}
