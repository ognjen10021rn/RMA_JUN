package rs.raf.vezbe11.presentation.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidplot.pie.Segment
import com.androidplot.pie.SegmentFormatter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentSavedmealslistBinding
import rs.raf.vezbe11.databinding.FragmentStatisticBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel

class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentStatisticBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFirstChart()//broj obroka po danima
        initSecondChart() //broj kalorija po danima
    }

    //TODO NASTAVICEMO OVO KADA ZAVRSIMO KREIRANJE PLANA,NE MOZE BEZ TOGA


    //broj obroka po danima
    private fun initFirstChart(){
        //https://www.youtube.com/watch?v=WJP5gSNyzLU

        //Trebamo da napravimo statistiku koliko hrane je dodato za svaki dan
        //u prethodnih 7 dana.Verovatno treba da imamo u bazi za savedFood datum dodavanja
        //pa da prolazimo kroz sacuvanu hranu i da pitamo da li ovaj datum spada u proteklih 7 dana

        val d0=0; //juce
        val d1=0; //prekjuce
        val d2=0; //pre 3 dana
        val d3=0;
        val d4=0;
        val d5=0;
        val d6=0;

        // inkrementiranje dana



        val s0=Segment("Danas",d0.toFloat())
        val s1=Segment("Pre 1 dana",d1.toFloat())
        val s2=Segment("Pre 2 dana",d2.toFloat())
        val s3=Segment("Pre 3 dana",d3.toFloat())
        val s4=Segment("Pre 4 dana",d4.toFloat())
        val s5=Segment("Pre 5 dana",d5.toFloat())
        val s6=Segment("Pre 6 dana",d6.toFloat())

        val color0=SegmentFormatter(Color.BLUE)
        val color1=SegmentFormatter(Color.LTGRAY)
        val color2=SegmentFormatter(Color.GREEN)
        val color3=SegmentFormatter(Color.YELLOW)
        val color4=SegmentFormatter(Color.CYAN)
        val color5=SegmentFormatter(Color.MAGENTA)
        val color6=SegmentFormatter(Color.GRAY)

    }

    //broj kalorija po danima
    private fun initSecondChart(){

        val d0=0; //juce
        val d1=0; //prekjuce
        val d2=0; //pre 3 dana
        val d3=0;
        val d4=0;
        val d5=0;
        val d6=0;

        // inkrementiranje dana

        val s0=Segment("Danas",d0.toFloat())
        val s1=Segment("Pre 1 dana",d1.toFloat())
        val s2=Segment("Pre 2 dana",d2.toFloat())
        val s3=Segment("Pre 3 dana",d3.toFloat())
        val s4=Segment("Pre 4 dana",d4.toFloat())
        val s5=Segment("Pre 5 dana",d5.toFloat())
        val s6=Segment("Pre 6 dana",d6.toFloat())

        val color0=SegmentFormatter(Color.BLUE)
        val color1=SegmentFormatter(Color.LTGRAY)
        val color2=SegmentFormatter(Color.GREEN)
        val color3=SegmentFormatter(Color.YELLOW)
        val color4=SegmentFormatter(Color.CYAN)
        val color5=SegmentFormatter(Color.MAGENTA)
        val color6=SegmentFormatter(Color.GRAY)

    }


}