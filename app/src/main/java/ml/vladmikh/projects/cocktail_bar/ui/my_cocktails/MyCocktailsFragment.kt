package ml.vladmikh.projects.cocktail_bar.ui.my_cocktails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.cocktail_bar.R
import ml.vladmikh.projects.cocktail_bar.databinding.FragmentMyCocktailsBinding

@AndroidEntryPoint
class MyCocktailsFragment : Fragment() {

    lateinit var binding: FragmentMyCocktailsBinding
    private val viewModel by viewModels<MyCocktailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyCocktailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.imageButtonAddCocktails.setOnClickListener{
            findNavController().navigate(R.id.action_myCocktailsFragment_to_addCocktailFragment)
        }


        viewModel.listCocktails.observe(this.viewLifecycleOwner) { cocktails ->
            if (cocktails.isNotEmpty()) {

                binding.arrow.visibility = View.GONE
                binding.addCocktailTextview.visibility = View.GONE
                binding.imageViewEmptyList.visibility = View.GONE


                val recyclerView = binding.recyclerViewCocktails
                recyclerView.visibility = View.VISIBLE
                val adapter = CocktailsAdapter()
                recyclerView.adapter = adapter

                cocktails.let {
                    adapter.submitList(it)
                }
            }else {
                binding.arrow.visibility = View.VISIBLE
                binding.addCocktailTextview.visibility = View.VISIBLE
                binding.imageViewEmptyList.visibility = View.VISIBLE

                binding.recyclerViewCocktails.visibility = View.GONE
            }
        }
    }

}