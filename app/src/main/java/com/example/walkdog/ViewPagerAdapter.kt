import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walkdog.Dogs
import com.example.walkdog.PerroViewModel
import com.example.walkdog.R
import com.google.android.gms.maps.model.LatLng

class ViewPagerAdapter(fragmentManager: FragmentManager, private val listaDeDogs: PerroViewModel) : FragmentPagerAdapter(fragmentManager) {



    override fun getItem(position: Int): Fragment {
        // Devuelve el fragmento correspondiente a la posición
        return when (position) {
            0 -> FirstFragment(listaDeDogs)
            1 -> SecondFragment(listaDeDogs)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        // Devuelve el número total de fragmentos
        return 2
    }
}

