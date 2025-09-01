map' :: (a -> b) -> [a] -> [b]
map' f [] = []
map' f (x:xs) = f x : map' f xs

-- map'' f = foldr (\x -> \r -> (:) (f x) r) []
map'' f = foldr (\x -> ((:) . f) x) []

foldr' :: (a -> b -> b) -> b -> [a] -> b
foldr' f z [] = z
foldr' f z (x:xs) = f x (foldr' f z xs)

-- z :: b
-- f :: a -> [a] -> b -> b
recr :: (a -> [a] -> b -> b) -> b -> [a] -> b
recr f z [] = z
recr f z (x:xs) = f x xs (recr f z xs)

length' :: [a] -> Int
length' [] = 0
length' (x:xs) = 1 + length' xs


-- length'' = recr (\x y r -> 1 + r) 0
-- length'' = recr (\x y -> (+ 1)) 0
-- length'' = recr (\x -> const (+ 1)) 0
-- length'' = recr (\x -> const (const (+ 1)) x) 0
length'' = recr (const (const (+ 1))) 0


-- mejorSegun :: (a -> a -> Bool) -> [a] -> a
-- mejorSegun f (x:[]) = x
-- mejorSegun f (x:y:xs) = if (f x y) then mejorSegun f (x:xs) else mejorSegun f (y:xs)

mejorSegun :: (a -> a -> Bool) -> [a] -> a
mejorSegun f = foldl1 (\x rec -> if f x rec then x else rec)

-- sumasParciales :: Num a => [a] -> [a]
-- sumasParciales xs = sumasParciales' [] xs 

-- sumasParciales' :: Num a => [a] -> [a] -> [a]
-- sumasParciales' ac [] = ac
-- sumasParciales' [] (x:xs) = sumasParciales' [x] xs
-- sumasParciales' ac (x:xs) = sumasParciales' (ac ++ [last ac + x]) xs

sumasParciales :: Num a => [a] -> [a]
sumasParciales xs = foldl (\rec x -> rec ++ [(+) x (if null rec then 0 else last rec)]) [] xs

