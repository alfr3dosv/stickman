package com.getgitman.gitman;
import java.util.List;
import java.util.stream.Collectors;

interface Displayable
{
    public void update();
    public char[][] getImage();
    public String getDialogs();
    public List<Entity> getEntitys();
}