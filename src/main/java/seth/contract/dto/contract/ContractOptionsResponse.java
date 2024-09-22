package seth.contract.dto.contract;

import lombok.Getter;
import seth.contract.dto.user.SimpleDto;

import java.util.List;

@Getter
public class ContractOptionsResponse {
    private final List<SimpleDto> types;
    private final List<SimpleDto> departments;

    public ContractOptionsResponse(List<SimpleDto> types, List<SimpleDto> departments) {
        this.types = types;
        this.departments = departments;
    }
}
