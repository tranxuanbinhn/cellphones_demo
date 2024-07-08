package com.cellphones10.service.impl;

import com.cellphones10.dto.BrandDTO;
import com.cellphones10.entity.BrandEntity;
import com.cellphones10.repository.BrandRepository;
import com.cellphones10.service.IBrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements IBrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        BrandEntity brand = mapper.map(brandDTO, BrandEntity.class);
        if(brandRepository.existsByBrandName(brand.getBrandName()))
        {
            throw new RuntimeException("");
        }
      BrandEntity brandSaved = brandRepository.save(brand);
      BrandDTO result = mapper.map(brandSaved, BrandDTO.class);

        return result;
    }

    @Override
    public List<BrandDTO> findAll(Pageable pageable) {
        List<BrandEntity> brandEntities =  brandRepository.findAll(pageable).getContent();
        List<BrandDTO> brandDTOS = new ArrayList<>();
        for (BrandEntity brandEntitie:brandEntities) {
            BrandDTO brandDTO = mapper.map(brandEntitie,BrandDTO.class);
            brandDTOS.add(brandDTO);
        }

        return brandDTOS;
    }
    public List<BrandDTO> findAll() {
        List<BrandEntity> brandEntities =  brandRepository.findAll();
        List<BrandDTO> brandDTOS = new ArrayList<>();
        for (BrandEntity brandEntitie:brandEntities) {
            BrandDTO brandDTO = mapper.map(brandEntitie,BrandDTO.class);
            brandDTOS.add(brandDTO);
        }

        return brandDTOS;
    }


    @Override
    public boolean delete(List<Long> list) {
        int countSizeList = list.size();
        for (Long id :list) {
            Optional<BrandEntity> brandEntity = brandRepository.findById(id);
            if(brandEntity.isPresent())
            {
                brandRepository.deleteById(id);
                countSizeList --;

            }
            else
            {
                throw  new RuntimeException("");
            }
        }
        if(countSizeList == 0)
        {
            return  true;
        }
        return false;
    }


    public Long CountPage()
    {
        return  brandRepository.count();
    }



}
