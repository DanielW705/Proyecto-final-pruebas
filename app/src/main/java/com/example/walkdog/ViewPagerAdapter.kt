import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walkdog.Dogs
import com.example.walkdog.R
import com.google.android.gms.maps.model.LatLng

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var Listadeperros: List<Dogs> = listOf(
        Dogs("Buddy", LatLng(37.7749, -122.4194)),
        Dogs("Max", LatLng(34.0522, -118.2437)),
        Dogs("Bella", LatLng(40.7128, -74.0060)),
        Dogs("Lucy", LatLng(51.5074, -0.1278))
    )

    override fun getItem(position: Int): Fragment {
        // Devuelve el fragmento correspondiente a la posición
        return when (position) {
            0 -> FirstFragment(Listadeperros)
            1 -> SecondFragment(Listadeperros)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        // Devuelve el número total de fragmentos
        return 2
    }
}

