package androidx.support.v4.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walhalla.common.ui.adapter.GalleryAdapter;
import com.walhalla.data.Data;
import com.walhalla.domen.ShellCommand;
import com.walhalla.domen.item.Obj;
import com.walhalla.fwdumper.MainActivity;
import com.walhalla.fwdumper.R;
import com.walhalla.ui.adapter.PaddingDecore;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ControlPanel extends Fragment implements GalleryAdapter.Presenter {


    private List<ShellCommand> mShellCommandList;

    private RecyclerView mRecyclerView;
    private GalleryAdapter mGalleryAdapter;

    public ControlPanel() {
    }

    public static ControlPanel newInstance() {
        return new ControlPanel();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mShellCommandList = new ArrayList<>();
        mShellCommandList.add(new ShellCommand("reboot", getString(R.string.shell_reboot), R.drawable.ic_reboot,
                android.R.color.holo_red_dark) {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });
        mShellCommandList.add(new ShellCommand("reboot recovery", getString(R.string.shell_reboot_to_recovery), R.drawable.ic_android_black_24dp,
                android.R.color.holo_blue_dark) {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });
        mShellCommandList.add(new ShellCommand("reboot bootloader", getString(R.string.shell_reboot_to_bootloader),
                R.drawable.ic_security, android.R.color.holo_green_dark) {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });

        mShellCommandList.add(new ShellCommand(null, "", -1, R.color.colorPrimary) {
            @Override
            public ArrayList<Obj> parseResult(List<String> result) {
                return null;
            }
        });

        if (mGalleryAdapter == null) {
            mGalleryAdapter = new GalleryAdapter(getContext(), mShellCommandList);
        }
        mGalleryAdapter.setPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        mRecyclerView.setLayoutParams(params);
//        mView = mRecyclerView;
        return inflater.inflate(R.layout.fragment_cp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);

        final int columns = getResources().getInteger(R.integer.gallery_columns);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), columns);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new PaddingDecore(8, columns));
        mRecyclerView.setAdapter(mGalleryAdapter);
    }

    @Override
    public void exec(String command) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.executeTheCommand(new Data.BaseCommand(command), true);
        }
    }
}
