package test.com.selfdefineview.collapse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import test.com.selfdefineview.R;

/**
 * Created by 立涛 on 2018/5/23.
 */

public class CollapseActivity extends AppCompatActivity{

    private SwipeRefreshLayout mRefreshSrl;
    private RecyclerView mContentRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapse_layout);

        mContentRv = (RecyclerView) findViewById(R.id.rv_content);
        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentRv.setAdapter(new ContentAdapter());

        mRefreshSrl = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        mRefreshSrl.setColorSchemeResources(R.color.red, R.color.blue);
    }

    private class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>{

        @Override
        public ContentAdapter.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentHolder(LayoutInflater.from(CollapseActivity.this).inflate(R.layout.item_simple_list_1, parent, false));
        }

        @Override
        public void onBindViewHolder(ContentAdapter.ContentHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 50;
        }

        class ContentHolder extends RecyclerView.ViewHolder{

            public ContentHolder(View itemView) {
                super(itemView);

            }
        }

    }
}
