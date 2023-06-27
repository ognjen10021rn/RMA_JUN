package rs.raf.vezbe11.presentation.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.androidplot.pie.Segment
import com.androidplot.pie.SegmentFormatter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.SavedFood
//import rs.raf.vezbe11.databinding.FragmentSavedmealslistBinding
import rs.raf.vezbe11.databinding.FragmentStatisticBinding
import rs.raf.vezbe11.presentation.contract.FoodContract
import rs.raf.vezbe11.presentation.view.states.SavedFoodState
import rs.raf.vezbe11.presentation.viewmodel.FoodViewModel
import java.time.LocalDate

class StatisticFragment : Fragment(R.layout.fragment_statistic) {

    private val foodViewModel: FoodContract.ViewModel by sharedViewModel<FoodViewModel>()
    private var _binding: FragmentStatisticBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var savedFoodList: List<SavedFood>? = null


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
        //initFirstChart()//broj obroka po danima
        //initSecondChart() //broj kalorija po danima
        init()
    }

    private fun init(){
        foodViewModel.savedFoodState.observe(viewLifecycleOwner, Observer {
            renderSavedFood(it)
        })
        foodViewModel.getAllSavedFood()

        //initFirstChart()
    }

    //TODO NASTAVICEMO OVO KADA ZAVRSIMO KREIRANJE PLANA,NE MOZE BEZ TOGA


    //broj obroka po danima
    private fun initFirstChart(){
        //https://www.youtube.com/watch?v=WJP5gSNyzLU

        //Trebamo da napravimo statistiku koliko hrane je dodato za svaki dan
        //u prethodnih 7 dana.Verovatno treba da imamo u bazi za savedFood datum dodavanja
        //pa da prolazimo kroz sacuvanu hranu i da pitamo da li ovaj datum spada u proteklih 7 dana

        val currentDate = LocalDate.now()

        val previousDays = currentDate.minusDays(7)
        var d0=0; //juce
        var d1=0; //prekjuce
        var d2=0; //pre 3 dana
        var d3=0;
        var d4=0;
        var d5=0;
        var d6=0;

        for (food in savedFoodList!!){
            val day = food.dayOfMonth - previousDays.dayOfMonth -1
            //println(day)
            if(day == 0)
                d6++
            if(day == 1)
                d5++
            if(day == 2)
                d4++
            if(day == 3)
                d3++
            if(day == 4)
                d2++
            if(day == 5)
                d1++
            if(day == 6)
                d0++
        }

        val s0=Segment("Danas", d0.toFloat())
        val s1=Segment("Pre 1 dana",d1.toFloat())
        val s2=Segment("Pre 2 dana",d2.toFloat())
        val s3=Segment("Pre 3 dana",d3.toFloat())
        val s4=Segment("Pre 4 dana",d4.toFloat())
        val s5=Segment("Pre 5 dana",d5.toFloat())
        val s6=Segment("Pre 6 dana",d6.toFloat())

        val color0=SegmentFormatter(Color.BLUE)
        val color1=SegmentFormatter(Color.RED)
        val color2=SegmentFormatter(Color.GREEN)
        val color3=SegmentFormatter(Color.YELLOW)
        val color4=SegmentFormatter(Color.CYAN)
        val color5=SegmentFormatter(Color.MAGENTA)
        val color6=SegmentFormatter(Color.BLACK)
        if(d0 > 0){
            binding.pieChart1.addSegment(s0, color0)
        }
        if(d1 > 0){
            binding.pieChart1.addSegment(s1, color1)
        }
        if(d2 > 0){
            binding.pieChart1.addSegment(s2, color2)
        }
        if(d3 > 0){
            binding.pieChart1.addSegment(s3, color3)
        }
        if(d4 > 0){
            binding.pieChart1.addSegment(s4, color4)
        }
        if(d5 > 0){
            binding.pieChart1.addSegment(s5, color5)
        }
        if(d6 > 0){
            binding.pieChart1.addSegment(s6, color6)
        }

    }

    //broj kalorija po danima
    private fun initSecondChart(){

        val currentDate = LocalDate.now()
        val previousDays = currentDate.minusDays(7)
        var d0=0.0; //juce
        var d1=0.0; //prekjuce
        var d2=0.0; //pre 3 dana
        var d3=0.0;
        var d4=0.0;
        var d5=0.0;
        var d6=0.0;
        for (food in savedFoodList!!){
            val day = food.dayOfMonth - previousDays.dayOfMonth -1
            //println(day)
            println(food.calories)
            if(day == 0)
                d6+=food.calories
            if(day == 1)
                d5+=food.calories
            if(day == 2)
                d4+=food.calories
            if(day == 3)
                d3+=food.calories
            if(day == 4)
                d2+=food.calories
            if(day == 5)
                d1+=food.calories
            if(day == 6)
                d0+=food.calories
        }

        // inkrementiranje dana

        val s0=Segment("Danas", d0)
        val s1=Segment("Pre 1 dana",d1)
        val s2=Segment("Pre 2 dana",d2)
        val s3=Segment("Pre 3 dana",d3)
        val s4=Segment("Pre 4 dana",d4)
        val s5=Segment("Pre 5 dana",d5)
        val s6=Segment("Pre 6 dana",d6)

        val color0=SegmentFormatter(Color.BLUE)
        val color1=SegmentFormatter(Color.RED)
        val color2=SegmentFormatter(Color.GREEN)
        val color3=SegmentFormatter(Color.YELLOW)
        val color4=SegmentFormatter(Color.CYAN)
        val color5=SegmentFormatter(Color.MAGENTA)
        val color6=SegmentFormatter(Color.BLACK)

        if(d0 > 0.0){
            binding.pieChart2.addSegment(s0, color0)
        }
        if(d1 > 0.0){
            binding.pieChart2.addSegment(s1, color1)
        }
        if(d2 > 0.0){
            binding.pieChart2.addSegment(s2, color2)
        }
        if(d3 > 0.0){
            binding.pieChart2.addSegment(s3, color3)
        }
        if(d4 > 0.0){
            binding.pieChart2.addSegment(s4, color4)
        }
        if(d5 > 0.0){
            binding.pieChart2.addSegment(s5, color5)
        }
        if(d6 > 0.0){
            binding.pieChart2.addSegment(s6, color6)
        }

    }

    private fun renderSavedFood(state: SavedFoodState){
        when(state){
            is SavedFoodState.Success -> {
                showLoadingState(false)
                //savedFoodAdapter.submitList(state.savedFoods)
                savedFoodList = state.savedFoods

                initFirstChart()
                initSecondChart()
                //initSecondChart()

            }
            is SavedFoodState.Success2 -> {
                showLoadingState(false)
                foodViewModel.setCurrentSavedFood(state.savedFood)
            }
            is SavedFoodState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedFoodState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(boolean: Boolean){

    }


}